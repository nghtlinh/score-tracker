package com.example.scoretracker.controller;

import com.example.scoretracker.exception.AppException;
import com.example.scoretracker.model.common.ErrorResponse;
import com.example.scoretracker.model.dto.login.LoginRequest;
import com.example.scoretracker.model.dto.login.LoginResponse;
import com.example.scoretracker.security.JwtTokenProvider;
import com.example.scoretracker.service.api.EmployeeService;
import com.example.scoretracker.service.login.CustomUserDetails;
import com.example.scoretracker.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private LoginService loginService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = loginService.login(request);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(userDetails);
            return new ResponseEntity<>(new LoginResponse(
                    String.format("Bearer %s", jwt),
                    employeeService.getEmployeeResource(userDetails)), HttpStatus.OK);

        } catch (AuthenticationException e) {
            throw new AppException(new ErrorResponse(String.format("System error => %s", e.getMessage())));
        }
    }

}
