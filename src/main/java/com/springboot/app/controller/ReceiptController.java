package com.springboot.app.controller;

import com.springboot.app.model.entity.Customer;
import com.springboot.app.model.entity.ItemReceipt;
import com.springboot.app.model.entity.Product;
import com.springboot.app.model.entity.Receipt;
import com.springboot.app.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/receipt")
@SessionAttributes("receipt")
public class ReceiptController {

    @Autowired
    private ICustomerService customerService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/form/{customerId}")
    public String create(@PathVariable("customerId") Long customerId, Model model, RedirectAttributes flash){

        Customer customer = customerService.findById(customerId);
        if(customer==null){
            flash.addFlashAttribute("error","Customer not exist into the database");
            return "redirect:/list";
        }

        Receipt receipt = new Receipt();
        receipt.setCustomer(customer);

        model.addAttribute("receipt",receipt);
        model.addAttribute("customer",customer);
        model.addAttribute("title","create receipt");
        return "receipt/form";
    }

    @GetMapping(value = "/load-products/{term}", produces = {"application/json"})
    public @ResponseBody List<Product> loadProducts(@PathVariable String term){
        return customerService.findByName(term);
    }

    @PostMapping("/form")
    public String save(@Valid Receipt receipt,
                       BindingResult result,
                       Model model,
                       @RequestParam(name="item_id[]", required = false) Long[] itemId,
                       @RequestParam(name="amount[]", required = false) Integer[] amount,
                       RedirectAttributes flash,
                       SessionStatus status){


        logger.info("entro en el metodo save");

        if(result.hasErrors()){
            model.addAttribute("title","Create receipt");
            logger.info("entro en al if de errores");
            return "receipt/form";
        }
        if(itemId==null || itemId.length==0){
            model.addAttribute("title","Create receipt");
            model.addAttribute("error","Error: receipt have to have lines");
            logger.info("entro en el if de item==null && item.lenght==0");
            return "receipt/form";
        }

        for (int i = 0; i<itemId.length;i++){
            Product product = customerService.findProductById(itemId[i]);

            ItemReceipt itemReceipt = new ItemReceipt();
            itemReceipt.setAmount(amount[i]);
            itemReceipt.setProduct(product);
            receipt.addItem(itemReceipt);
        }

        customerService.saveReceipt(receipt);
        logger.info("se completo correctamente");
        status.setComplete();
        flash.addFlashAttribute("success","Receipt created successful");


        return "redirect:/see/"+receipt.getCustomer().getId();
    }

    @GetMapping("/see/{id}")
    public String see(@PathVariable("id") Long id, Model model, RedirectAttributes flash){

        Receipt receipt = customerService.fetchReceiptByIdWithCustomerWithItemReceiptWithProduct(id);

        if(receipt==null){
            flash.addFlashAttribute("error","The receipt not exist into the databse");
            return "redirect:/list";
        }
        model.addAttribute("receipt",receipt);
        model.addAttribute("title","Receipt: ".concat(receipt.getDescription()));
        return"receipt/see";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes flash){

        Receipt receipt = customerService.findReceiptById(id);
        if(receipt!=null){
           customerService.deleteReceipt(id);
           flash.addFlashAttribute("success","receipt deleted successful");
           return "redirect:/see/"+receipt.getCustomer().getId();
        }
        flash.addFlashAttribute("error","receipt not exist into the database");
        return"redirect:/list";
    }
}

