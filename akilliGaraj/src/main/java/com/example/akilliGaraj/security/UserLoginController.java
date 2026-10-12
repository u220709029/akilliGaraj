package com.example.akilliGaraj.security;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {
     private final MyUserDetailsService myUserDetailsService;
     private final PasswordEncoder passwordEncoder;

    public UserLoginController(MyUserDetailsService myUserDetailsService, PasswordEncoder passwordEncoder) {
        this.myUserDetailsService = myUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){

        try{
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginDto.getUserName());
            //kullanıc varsa sifreye bakcaz
            boolean matches = passwordEncoder.matches(loginDto.getPassword(),userDetails.getPassword());
            if (matches){
                return ResponseEntity.ok("GİRİS BASARILI");
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("sifre hatalı");
            }
        }catch (UsernameNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("kayıtlı kullanıcı bulunamadı");

        }catch (Exception exception){
            return ResponseEntity.badRequest().build();
        }

    }
}
