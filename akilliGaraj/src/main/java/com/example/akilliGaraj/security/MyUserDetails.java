package com.example.akilliGaraj.security;


import com.example.akilliGaraj.entities.User2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    // Kendi oluşturduğumuz Entity sınıfını içeri alıyoruz
    private final User2 user;

    public MyUserDetails(User2 user) {
        this.user = user;
    }

    // 1. Kullanıcının rollerini Spring Security formatına çevirme
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // SimpleGrantedAuthority, GrantedAuthority interface'ini implemente eden hazır bir sınıftır.
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    // 2. Şifreyi döndürme
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 3. Kullanıcı adını döndürme
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // Videoda bu derste sadece üstteki 3 metodun implemente edildiği,
    // isAccountNonExpired, isAccountNonLocked gibi diğer metotlara sonra değinileceği belirtildi.
}