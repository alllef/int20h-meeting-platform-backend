package com.github.alllef.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class SessionIdentifier {
    private final String sessionName;
    private final String sessionUrlId;
}
