package com.github.alllef.service;

import io.openvidu.java.client.*;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public class ConnectionService {

    public String createConnection(Session session, OpenViduRole role) throws OpenViduJavaClientException, OpenViduHttpException {
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(role)
                .data()
                .build();

        Connection connection = session.createConnection(connectionProperties);
        return connection.getToken();
    }

}