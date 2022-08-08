package com.springboot.app.controller;


import com.springboot.app.model.entity.Customer;
import com.springboot.app.service.ICustomerService;
import com.springboot.app.service.IUploadFileService;
import com.springboot.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Locale;


@SessionAttributes("customer")
@Controller
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IUploadFileService iUploadFileService;

    private final static String UPLOADS_FOLDER = "uploads";
    @Autowired
    private MessageSource  messageSource;


    @Secured("ROLE_USER")
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> seePhoto(@PathVariable String filename) {

        Resource resource = null;

        try {
            resource = iUploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Secured("ROLE_USER")
    @GetMapping("/see/{id}")
    public String see(@PathVariable("id") Long id, Model model, RedirectAttributes flash,  Locale locale) {

        Customer customer = customerService.fetchByIdWithReceipts(id);
        if (customer == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("text.customer.flash.db.error",null,locale));
            return "redirect:/list";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("title",  messageSource.getMessage("text.customer.detail.title",null,locale));
        return "see";
    }
    @GetMapping({"/list","/"})
    public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Locale locale) {

        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Customer> customers = customerService.findAll(pageRequest);
        PageRender<Customer> pageRender = new PageRender<>("/list", customers);

        model.addAttribute("title",messageSource.getMessage("text.customer.list",null,locale));
        model.addAttribute("customers", customers);
        model.addAttribute("page", pageRender);
        return "list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String create(Model model,Locale locale) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("title", messageSource.getMessage("text.customer.form.title",null,locale));
        return "form";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/form/{id}")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes flash,Locale locale) {
        Customer customer = null;
        if (id > 0) {
            customer = customerService.findById(id);
            if (customer == null) {
                flash.addFlashAttribute("error", messageSource.getMessage("text.customer.flash.db.error",null,locale));
                return "redirect:/list";
            }
        } else {
            flash.addFlashAttribute("error", messageSource.getMessage("text.customer.flash.id.error",null,locale));
            return "redirect:/list";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("title", messageSource.getMessage("text.customer.form.title.edit",null,locale));
        return "form";
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String create(@Valid Customer customer, BindingResult result, RedirectAttributes flash, Model model,
                         @RequestParam("file") MultipartFile photo, Locale locale) {

        if (result.hasErrors()) {
            model.addAttribute("title", messageSource.getMessage("text.customer.form.title",null,locale));
            return "form";
        }

        if (!photo.isEmpty()) {
            ////
            if (customer.getId() != null && customer.getId() > 0
                    && customer.getPhoto() != null && customer.getPhoto().length() > 0) {

                iUploadFileService.delete(customer.getPhoto());
            }

            String uniqueFileName = null;
            try {
                uniqueFileName = iUploadFileService.copy(photo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            flash.addFlashAttribute("info", messageSource.getMessage("text.customer.flash.photo.goUp.success", null, locale) + "'" + uniqueFileName + "'");

            customer.setPhoto(uniqueFileName);

        }

        String message = (customer.getId() != null) ? messageSource.getMessage("text.customer.flash.edit.success", null, locale) : messageSource.getMessage("text.customer.flash.create.success", null, locale);
        customerService.save(customer);
        flash.addFlashAttribute("success", message);
        return "redirect:list";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash,Locale locale) {

        if (id > 0) {
            Customer customer = customerService.findById(id);
            customerService.delete(id);
            flash.addFlashAttribute("success", messageSource.getMessage("text.customer.flash.delete.success",null,locale));

            if (iUploadFileService.delete(customer.getPhoto())) {
                flash.addFlashAttribute("info", messageSource.getMessage("text.customer.flash.photo.delete.success ",null,locale));
            }

        }
        return "redirect:/list";
    }

    private boolean hasRole(String role){
        SecurityContext context = SecurityContextHolder.getContext();
        if(context==null){
            return false;
        }
        Authentication authentication = context.getAuthentication();

        if(authentication==null){
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority: authorities) {
            if(role.equals(authority.getAuthority())){
                return true;
            }
            
        }
        return false;
    }
}
