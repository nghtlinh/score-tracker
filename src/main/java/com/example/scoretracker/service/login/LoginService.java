package com.example.scoretracker.service.login;

import com.example.scoretracker.model.dto.login.LoginRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authManager;

    public Authentication login(LoginRequest form) {
        Authentication request = new UsernamePasswordAuthenticationToken(form.getUsername(),
                                                                         form.getPassword());
        Authentication authentication = authManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
