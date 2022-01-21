package com.github.alllef.service;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.SessionProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionService {
    private final OpenVidu openVidu;

    private void createSession(){
        try {
            openVidu.createSession();
        } catch (OpenViduJavaClientException e) {
            e.printStackTrace();
        } catch (OpenViduHttpException e) {
            e.printStackTrace();
        }
    }

}
