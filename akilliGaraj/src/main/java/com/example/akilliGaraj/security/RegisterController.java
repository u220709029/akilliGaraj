package com.example.akilliGaraj.security;

import com.example.akilliGaraj.entities.User2;
import com.example.akilliGaraj.enums.RoleEnum;
import com.example.akilliGaraj.repository.UserRepository2;
import com.example.akilliGaraj.security.MyUserDetails;
import com.example.akilliGaraj.security.MyUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final MyUserDetailsService myUserDetailsService;
    private final UserRepository2 userRepository2;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(MyUserDetailsService myUserDetailsService,
                              UserRepository2 userRepository2, PasswordEncoder passwordEncoder) {
        this.myUserDetailsService = myUserDetailsService;
        this.userRepository2 = userRepository2;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String userName,
            @RequestParam String password){

        try {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(userName);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("kullanıcı zaten var");
        }catch (UsernameNotFoundException exception){
            User2 user = new User2();
            user.setUsername(userName);
            user.setRole(RoleEnum.ROLE_USER);
            user.setPassword(passwordEncoder.encode(password));
            User2 savedUser = userRepository2.save(user);
            return ResponseEntity.ok().body("kayıt alındı");


        }catch (Exception exception){
            return ResponseEntity.badRequest().build();
        }
    }


}
