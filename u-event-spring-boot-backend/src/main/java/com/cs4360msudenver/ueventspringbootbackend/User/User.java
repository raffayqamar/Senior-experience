package com.cs4360msudenver.ueventspringbootbackend.User;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
//    For OAuth2 (Google and Facebook, etc.)
    @Enumerated(EnumType.STRING)
    private ProviderEnum provider;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
        // This was updated to add the role "ROLE_USER" to the list of authorities
        return Collections.singleton(
                new SimpleGrantedAuthority("USER")
        );
    }


//    This is for OAuth2 (Google and Facebook, etc.)
    public ProviderEnum getProvider() {
        return provider;
    }

    public void setProvider(ProviderEnum provider) {
        this.provider = provider;
    }


    @Override
    public String getUsername() {
//        return null;
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return false;
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return false;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return false;
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return false;
        return true;
    }

}



