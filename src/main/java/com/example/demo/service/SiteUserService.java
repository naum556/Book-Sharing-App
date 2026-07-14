package com.example.demo.service;

import com.example.demo.models.Book;
import com.example.demo.models.Role;
import com.example.demo.models.SiteUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface SiteUserService extends UserDetailsService {
    SiteUser register(String username, String password, String confirmPassword, String email, Role role);
    SiteUser findById(String username);
    List<Book> findBooksByUser(String username);
}
