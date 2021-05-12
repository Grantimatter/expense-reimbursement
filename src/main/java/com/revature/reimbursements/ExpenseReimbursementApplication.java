package com.revature.reimbursements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ExpenseReimbursementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseReimbursementApplication.class, args);
    }

}
