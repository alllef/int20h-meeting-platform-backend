package com.github.alllef.entity;

import lombok.Data;

@Data
public class User {
    private final String name;
    private SessionIdentifier sessionIdentifier;
}
