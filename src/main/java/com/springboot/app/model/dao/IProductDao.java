package com.springboot.app.model.dao;

import com.springboot.app.model.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductDao extends CrudRepository<Product,Long> {

    public List<Product> findByNameLikeIgnoreCase(String name);
}
