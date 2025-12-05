package com.BIT.BCMS.service;

import com.BIT.BCMS.entities.Users;
import java.util.List;

public interface UserService {
    List<Users> findAll();
    Users findById(Long id);
    Users create(Users user);
    Users update(Long id, Users user);
    void delete(Long id);
    String verify(Users user);
}