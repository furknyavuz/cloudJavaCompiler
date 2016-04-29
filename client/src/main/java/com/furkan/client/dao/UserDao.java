package com.furkan.client.dao;

import com.furkan.client.entity.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {

    public User findByUsernameAndPassword(String username, String password);
}
