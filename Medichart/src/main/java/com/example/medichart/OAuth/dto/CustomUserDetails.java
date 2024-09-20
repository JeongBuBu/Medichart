package com.example.medichart.OAuth.dto;

import com.example.medichart.OAuth.entity.SocialUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails  implements UserDetails {

    private final SocialUserEntity socialUserEntity;

    public CustomUserDetails(SocialUserEntity socialUserEntity) {

        this.socialUserEntity = socialUserEntity;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return socialUserEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return socialUserEntity.getPassword();
    }

    @Override
    public String getUsername() {

        return socialUserEntity.getUsername();
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

