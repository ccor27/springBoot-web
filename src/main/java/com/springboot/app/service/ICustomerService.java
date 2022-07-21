package com.springboot.app.service;

import com.springboot.app.model.entity.Customer;
import com.springboot.app.model.entity.Product;
import com.springboot.app.model.entity.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ICustomerService {

    List<Customer> findAll();
    Page<Customer> findAll(Pageable pageable);
    public void save(Customer customer);

    public Customer findById(Long id);

    public void delete(Long id);

    public List<Product> findByName(String term);

    public void saveReceipt(Receipt receipt);

    public Product findProductById(Long id);

    public Receipt findReceiptById(Long id);

    public void deleteReceipt(Long id);
}
