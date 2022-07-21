package com.springboot.app.controller;


import com.springboot.app.model.entity.Customer;
import com.springboot.app.service.ICustomerService;
import com.springboot.app.service.IUploadFileService;
import com.springboot.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.io.IOException;
import java.net.MalformedURLException;


@SessionAttributes("customer")
@Controller
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IUploadFileService iUploadFileService;

    private final static String UPLOADS_FOLDER = "uploads";


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

    @GetMapping("/see/{id}")
    public String see(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {

        Customer customer = customerService.findById(id);
        if (customer == null) {
            flash.addFlashAttribute("error", "The customer not exist into the database");
            return "redirect:/list";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Customer detail: " + customer.getLastName());
        return "see";
    }

    @GetMapping("/list")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Customer> customers = customerService.findAll(pageRequest);
        PageRender<Customer> pageRender = new PageRender<>("/list", customers);

        model.addAttribute("title", "List of customers");
        model.addAttribute("customers", customers);
        model.addAttribute("page", pageRender);
        return "list";
    }

    @GetMapping("/form")
    public String create(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Form to create a customer");
        return "form";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {
        Customer customer = null;
        if (id > 0) {
            customer = customerService.findById(id);
            if (customer == null) {
                flash.addFlashAttribute("error", "The ID not exist into the DataBase");
                return "redirect:/list";
            }
        } else {
            flash.addFlashAttribute("error", "The ID cant not be zero");
            return "redirect:/list";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Edit customer");
        return "form";
    }

    @PostMapping("/form")
    public String create(@Valid Customer customer, BindingResult result, RedirectAttributes flash, Model model,
                         @RequestParam("file") MultipartFile photo) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Form to create a customer");
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

            flash.addFlashAttribute("info", "You upload photo '" + uniqueFileName + "' successful");

            customer.setPhoto(uniqueFileName);

        }

        String message = (customer.getId() != null) ? "Customer edited successful" : "Customer created successful";
        customerService.save(customer);
        flash.addFlashAttribute("success", message);
        return "redirect:list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            Customer customer = customerService.findById(id);
            customerService.delete(id);
            flash.addFlashAttribute("success", "Customer deleted successful");

            if (iUploadFileService.delete(customer.getPhoto())) {
                flash.addFlashAttribute("info", "photo: " + customer.getPhoto() + " deleted successful");
            }

        }
        return "redirect:/list";
    }
}
