package com.springboot.app.service;

import com.springboot.app.model.dao.ICustomerDao;
import com.springboot.app.model.dao.IProductDao;
import com.springboot.app.model.dao.IReceiptDao;
import com.springboot.app.model.entity.Customer;
import com.springboot.app.model.entity.Product;
import com.springboot.app.model.entity.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService{

    @Autowired
    private ICustomerDao customerDao;
    @Autowired
    private IProductDao productDao;
    @Autowired
    private IReceiptDao receiptDao;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return (List<Customer>) customerDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        return customerDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Customer customer) {
      customerDao.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
      customerDao.deleteById(id);
    }

    @Override
    public List<Product> findByName(String term) {
        return productDao.findByNameLikeIgnoreCase("%"+term+"%");
    }

    @Override
    @Transactional
    public void saveReceipt(Receipt receipt) {
     receiptDao.save(receipt);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Receipt findReceiptById(Long id) {
        return receiptDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteReceipt(Long id) {
     receiptDao.deleteById(id);
    }
}
