package com.springboot.app.model.dao;

import com.springboot.app.model.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICustomerDao extends PagingAndSortingRepository<Customer,Long> {

}
