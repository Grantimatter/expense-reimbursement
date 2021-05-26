package com.revature.reimbursements.controller;

import com.revature.reimbursements.model.dto.LoginDTO;
import com.revature.reimbursements.util.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    private static final Logger log = LogManager.getLogger(JwtController.class);

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public JwtController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/jwt")
    public String welcome() {
        return "Welcome to reimbursements";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword())
            );
        } catch (AuthenticationException e) {
            log.debug("Invalid username and/or password");
            return "";
        }

        return jwtUtil.generateToken(loginDTO.getUsernameOrEmail());

    }
}
