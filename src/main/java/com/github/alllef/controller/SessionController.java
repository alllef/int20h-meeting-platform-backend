package com.github.alllef.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.alllef.entity.User;
import com.github.alllef.service.ConnectionService;
import com.github.alllef.service.SessionService;
import com.google.gson.JsonObject;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final ConnectionService connectionService;

    @PostMapping("/connect/{session-id}")
    ResponseEntity<JSONObject> connect(@PathVariable("session-id") String sessionUrlId, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("userName");

        if (user.getSessionIdentifier().getSessionUrlId().equals(sessionUrlId)) {
            var optSession = sessionService.createSession(user.getSessionIdentifier());
            if (optSession.isPresent()) {
                String token = connectionService.createConnection(optSession.get(), OpenViduRole.MODERATOR);
                JSONObject json = new JSONObject();
                json.put("token", token);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }
        } else {
            Session session = sessionService.getSession(user.getSessionIdentifier())
                    .orElseThrow();
            connectionService.createConnection(session, OpenViduRole.PUBLISHER);
        }

    }

}
