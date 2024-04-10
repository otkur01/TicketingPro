package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CrudService<T,ID> {

    T save(T user);
    T findById(ID username);
    List<T> findAll();
    void deleteById(ID username);



}

