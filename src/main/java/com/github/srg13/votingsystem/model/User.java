package com.github.srg13.votingsystem.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class User extends NamedEntity {

    private String email;

    private String password;

    private LocalDateTime registered;

    @Setter(AccessLevel.NONE)
    private Set<Role> roles;
}
