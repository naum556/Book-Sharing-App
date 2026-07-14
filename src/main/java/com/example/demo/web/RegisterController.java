package com.example.demo.web;

import com.example.demo.models.exceptions.InvalidArgumentsException;
import com.example.demo.models.exceptions.PasswordsDoNotMatchException;
import com.example.demo.models.exceptions.UsernameAlreadyExistsException;
import com.example.demo.service.AuthService;
import com.example.demo.service.SiteUserService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.models.Role;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final AuthService authService;
    private final SiteUserService siteUserService;

    public RegisterController(AuthService authService, SiteUserService siteUserService) {
        this.authService = authService;
        this.siteUserService = siteUserService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "registration";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String email,
                           @RequestParam Role role) {

        try{
            this.siteUserService.register(username, password, confirmPassword, email,role);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException | UsernameAlreadyExistsException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }

    }
}
