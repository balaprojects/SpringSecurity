package com.frankmoley.security.app.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class LandonUserPrincipal implements UserDetails{

    private User user;
    private List<AuthUserGroup> authUserGroup;

    public LandonUserPrincipal(User user, List<AuthUserGroup> authUserGroup){
        super();
        this.user = user;
        this.authUserGroup = authUserGroup;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authUserGroup == null) {
            return Collections.EMPTY_SET;
        }
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        authUserGroup.forEach(authUserGroup1 ->
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authUserGroup1.getAuthGroup())));
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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
        return true;
    }
}
