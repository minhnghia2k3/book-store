package com.minhnghia2k3.book.store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/healthcheck")
    public ResponseEntity<HashMap<String,String>> healthCheck() {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Server is healthy");
        return ResponseEntity.ok(map);
    }

}
