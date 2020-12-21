package com.github.srg13.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "registered_time", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime registered = LocalDateTime.now();

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = EnumSet.of(Role.USER);

    public User(Integer id, String name, @Email @NotBlank @Size(max = 100) String email,
                @NotBlank @Size(min = 5, max = 100) String password,
                LocalDateTime registered, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
        this.password = password;
        this.registered = registered;
    }

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getRegistered(), u.isEnabled(), u.getRoles());
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                ", email='" + email +
                ", registered=" + registered +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
