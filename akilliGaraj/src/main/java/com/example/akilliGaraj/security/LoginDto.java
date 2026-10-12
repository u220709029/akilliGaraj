package com.example.akilliGaraj.security;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginDto {
    @NotBlank(message = "username bos olamaaz")
    private String userName;
    @NotBlank(message = "username bos olamaaz")
    @Size(min = 2,message = "uzunluk en az 2 karakter olmalı")
    private String password;
}
