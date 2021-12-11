package com.demo.notes.service;

import com.demo.notes.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements CRUDService<User>{
    @Override
    public List<User> listAll() {
        return null;
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
