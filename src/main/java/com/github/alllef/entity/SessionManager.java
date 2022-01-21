package com.github.alllef.entity;

import io.openvidu.java.client.Session;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private final Map<SessionIdentifier, Session> sessions = new ConcurrentHashMap<>();

    public boolean addSession(SessionIdentifier sessionName, Session session) {
        if (sessions.containsKey(sessionName))
            return false;

        sessions.put(sessionName, session);
        return true;
    }

    public boolean removeSession(SessionIdentifier sessionName) {
        if (sessions.containsKey(sessionName)) {
            sessions.remove(sessionName);
            return true;
        }

        return false;
    }

    public Optional<Session> getSession(SessionIdentifier sessionName) {
        return Optional.ofNullable(sessions.get(sessionName));
    }
}
