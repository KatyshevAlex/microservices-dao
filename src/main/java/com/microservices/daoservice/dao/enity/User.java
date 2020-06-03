package com.microservices.daoservice.dao.enity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users", schema = "accounts")
@SequenceGenerator(name = "sq_users", sequenceName = "sq_users", allocationSize = 1, schema = "accounts")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_users")
    @Column(name = "id")
    private Long id;

    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    private int loginAttempt;
    private boolean tokenExpired;
    private LocalDateTime lastSeen;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinTable(
            schema = "accounts",
            name = "users_roles",
            joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn( name = "role_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("users")
    private Collection<Role> roles;
}