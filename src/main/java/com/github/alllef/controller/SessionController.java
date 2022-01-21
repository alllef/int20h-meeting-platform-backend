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
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

        if (user.getSessionIdentifier().getSessionUrlId().equals(sessionUrlId))
            return createSession(user);
        else
            return createSession(user);
    }

    @PostMapping("/remove-user")
    ResponseEntity<JSONObject> removeUser(@RequestBody String sessionNameToken, HttpSession httpSession) throws Exception {
        JSONObject sessionNameTokenJSON = (JSONObject) new JSONParser().parse(sessionNameToken);
        String sessionName = (String) sessionNameTokenJSON.get("sessionName");
        String userToken = (String) sessionNameTokenJSON.get("userToken");
        String moderatorToken = (String) sessionNameTokenJSON.get("moderatorToken");

        if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {

            // If the token exists
            if (this.mapSessionNamesTokens.get(sessionName).remove(userToken) != null) {
                // User left the session
                if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
                    // Last user left: session must be removed
                    this.mapSessions.remove(sessionName);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // The TOKEN wasn't valid
                System.out.println("Problems in the app server: the TOKEN wasn't valid");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            // The SESSION does not exist
            System.out.println("Problems in the app server: the SESSION does not exist");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<JSONObject> createSession(User user) {
        var optSession = sessionService.createSession(user.getSessionIdentifier());
        if (optSession.isPresent()) {
            String token = connectionService.createConnection(optSession.get(), OpenViduRole.MODERATOR);
            JSONObject json = new JSONObject();
            json.put("token", token);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return null;
    }

    private ResponseEntity<JSONObject> createConnection(User user) {
        Session session = sessionService.getSession(user.getSessionIdentifier())
                .orElseThrow();
        connectionService.createConnection(session, OpenViduRole.PUBLISHER);

    }
}
