package com.example.demo.service;

import com.example.demo.models.SiteUser;

import java.util.List;

public interface AuthService {
    SiteUser login(String username, String password);

    List<SiteUser> findAll();


}
