package com.example.scoretracker.service.login;

import com.example.scoretracker.model.entity.EmployeeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private EmployeeEntity user;

    private Set<GrantedAuthority> customAuthorities;

    public CustomUserDetails(EmployeeEntity user, Set<GrantedAuthority> authorities) {
        this.user = user;
        this.customAuthorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return customAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
