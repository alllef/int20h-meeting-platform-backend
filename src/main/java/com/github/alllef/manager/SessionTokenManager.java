package com.github.alllef.manager;

import com.github.alllef.entity.SessionIdentifier;
import io.openvidu.java.client.OpenViduRole;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionTokenManager {
    private final Map<SessionIdentifier, Map<String, OpenViduRole>> sessionsWithTokens = new ConcurrentHashMap<>();

    public Optional<Map<String, OpenViduRole>> getConnectionsBySession(SessionIdentifier sessionIdentifier) {
        return Optional.ofNullable(sessionsWithTokens.get(sessionIdentifier));
    }

    public boolean addConnection(SessionIdentifier sessionIdentifier, String token, OpenViduRole openViduRole) {
        if (getConnectionsBySession(sessionIdentifier).isEmpty()) {
            sessionsWithTokens.put(sessionIdentifier, new ConcurrentHashMap<>());
            sessionsWithTokens.get(sessionIdentifier)
                    .put(token, openViduRole);

            return true;
        } else {
            if (!sessionsWithTokens.get(sessionIdentifier)
                    .containsKey(token)) {
                sessionsWithTokens.get(sessionIdentifier)
                        .put(token, openViduRole);
                return true;
            }
        }
        return false;
    }

    public boolean removeConnection(SessionIdentifier sessionIdentifier, String token) {
        if (getConnectionsBySession(sessionIdentifier).isEmpty())
            return false;

        else if (!sessionsWithTokens.get(sessionIdentifier).containsKey(token))
            return false;

        sessionsWithTokens.get(sessionIdentifier)
                .remove(token);

        return true;
    }
}
