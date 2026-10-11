package com.example.akilliGaraj.repository;

import com.example.akilliGaraj.entities.User2;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository2 extends JpaRepository<User2, Integer> {

    // Kullanıcı adına göre veritabanından kullanıcıyı getirecek özel metot
    Optional<User2> findByUsername(String username);
}