package com.github.alllef.service;

import com.github.alllef.entity.SessionIdentifier;
import com.github.alllef.manager.SessionManager;
import io.openvidu.java.client.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService {
    private final OpenVidu openVidu;
    private final SessionManager sessionManager;

    public Optional<Session> createSession(SessionIdentifier sessionIdentifier) throws OpenViduJavaClientException, OpenViduHttpException {
        if (sessionManager.getSession(sessionIdentifier)
                .isPresent())
            return Optional.empty();

            Session newSession = openVidu.createSession();
            return Optional.of(newSession);
    }

    public Optional<Session> getSession(SessionIdentifier sessionIdentifier){
        return sessionManager.getSession(sessionIdentifier);
    }
}
