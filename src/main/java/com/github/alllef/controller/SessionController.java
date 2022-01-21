package com.github.alllef.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
    @PostMapping("/session/create")
    ResponseEntity<JsonNode> createSession() {
        return null;
    }
}
