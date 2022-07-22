package com.springboot.app.model.dao;

import com.springboot.app.model.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICustomerDao extends PagingAndSortingRepository<Customer,Long> {
    @Query("select c from Customer c left join fetch c .receipts r where c.id=?1")
    public Customer fetchByIdWithReceipts(Long id);

}
