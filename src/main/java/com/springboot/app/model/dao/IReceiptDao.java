package com.springboot.app.model.dao;

import com.springboot.app.model.entity.Receipt;
import org.springframework.data.repository.CrudRepository;

public interface IReceiptDao extends CrudRepository<Receipt,Long> {
}
