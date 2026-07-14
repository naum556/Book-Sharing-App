package com.example.demo.service.impl;

import com.example.demo.models.SiteUser;
import com.example.demo.models.exceptions.InvalidArgumentsException;
import com.example.demo.models.exceptions.InvalidUserCredentialsException;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final SiteUserRepository siteUserRepository;

    public AuthServiceImpl(SiteUserRepository siteUserRepository) {
        this.siteUserRepository = siteUserRepository;
    }

    @Override
    public SiteUser login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        return siteUserRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public List<SiteUser> findAll() {
        return siteUserRepository.findAll();
    }
}
