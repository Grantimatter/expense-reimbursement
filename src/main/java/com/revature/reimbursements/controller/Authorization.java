package com.revature.reimbursements.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class Authorization {

    @GetMapping
    public ResponseEntity<Map<String, Object>> authTest()
    {
        Map<String, Object> model = new HashMap();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello Wrold!");
        return ResponseEntity.ok(model);
    }
}
