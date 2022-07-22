package com.springboot.app.model.dao;

import com.springboot.app.model.entity.Receipt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IReceiptDao extends CrudRepository<Receipt,Long> {
    @Query("select r from Receipt r join fetch r.customer c join fetch r.items l join fetch l.product where r.id=?1")
    public Receipt fetchByIdWithCustomerWithItemReceiptWithProduct(Long id);
}
