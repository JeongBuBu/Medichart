package com.example.medichart.UserLogin.dto;


import com.example.medichart.UserLogin.entity.NormalUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final NormalUserEntity normalUserEntity;

    public CustomUserDetails(NormalUserEntity normalUserEntity) {
        this.normalUserEntity = normalUserEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> normalUserEntity.getRole()); // 람다를 사용하여 권한 설정
        return collection;
    }

    @Override
    public String getPassword() {
        return normalUserEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return normalUserEntity.getEmail();
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