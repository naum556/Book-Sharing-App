package com.example.demo.service.impl;


import com.example.demo.models.Book;
import com.example.demo.models.Role;
import com.example.demo.models.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.SiteUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements SiteUserService {

    private final SiteUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(SiteUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SiteUser register(String username, String password, String confirmPassword, String email, Role role) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty() || confirmPassword==null || confirmPassword.isEmpty() ||  email==null || email.isEmpty())
            throw new RuntimeException();
        if(!password.equals(confirmPassword))
            throw new RuntimeException();
        if(this.userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException(username+" already exists");
        }

        SiteUser siteUser = new SiteUser(username, passwordEncoder.encode(password), email,role);
        return this.userRepository.save(siteUser);

    }

    @Override
    public SiteUser findById(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<Book> findBooksByUser(String username) {
        SiteUser user = this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Book> userBooks = user.getBooks();
        return userBooks;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException());
    }
}
