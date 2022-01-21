package com.github.alllef.entity;

import io.openvidu.java.client.Session;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public boolean addSession(String sessionName, Session session) {
        if (sessions.containsKey(sessionName))
            return false;

        sessions.put(sessionName, session);
        return true;
    }

    public boolean removeSession(String sessionName) {
        if (sessions.containsKey(sessionName)) {
            sessions.remove(sessionName);
            return true;
        }

        return false;
    }

    public Optional<Session> getSession(String sessionName) {
        return Optional.ofNullable(sessions.get(sessionName));
    }
}
