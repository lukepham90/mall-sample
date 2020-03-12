package com.uuhnaut69.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uuhnaut69.mall.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public class UserPrinciple implements UserDetails {

    private static final long serialVersionUID = 1L;

    private UUID id;

    private String name;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private boolean isEnabled;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(UUID id, String name, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.isEnabled = isEnabled;
    }

    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority(user.getRole().toString()));

        return new UserPrinciple(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPassword(),
                authorities, user.isEnabled());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

}