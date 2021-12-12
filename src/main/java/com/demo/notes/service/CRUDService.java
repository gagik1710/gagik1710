package com.demo.notes.service;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CRUDService<T> {
    List<T> listAll(PageRequest of);

    T getById(Long id);

    T saveOrUpdate(T domainObject);

    void delete(Long id);
}