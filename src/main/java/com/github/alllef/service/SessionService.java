package com.github.alllef.service;

import com.github.alllef.entity.SessionManager;
import io.openvidu.java.client.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionService {
    private final OpenVidu openVidu;
    private final SessionManager sessionManager;

    private void createSession(String sessionName) {
        try {
            Session newSession = openVidu.createSession();
            sessionManager.addSession(sessionName,newSession);
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
        }
    }

}
