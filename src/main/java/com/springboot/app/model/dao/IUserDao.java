package com.springboot.app.model.dao;

import com.springboot.app.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserDao extends CrudRepository<User,Long> {

    public User findByUsername(String username);

}
