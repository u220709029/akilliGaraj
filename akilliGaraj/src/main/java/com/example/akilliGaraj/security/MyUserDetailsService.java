package com.example.akilliGaraj.security;

import com.example.akilliGaraj.entities.User2;
import com.example.akilliGaraj.repository.UserRepository2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository2 userRepository;

    // Constructor Injection (Enjekte Etme)
    public MyUserDetailsService(UserRepository2 userRepository) {
        this.userRepository = userRepository;
    }

    // Spring Security bu metodu giriş yaparken otomatik çağırır
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Veritabanına gidip username ile kullanıcıyı arıyoruz
        User2 user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı yok")); // Bulamazsa fırlat

        // Eğer kullanıcı varsa, bunu Spring Security'nin anlayacağı MyUserDetails yapısına sarıp gönderiyoruz
        return new MyUserDetails(user);
    }
}