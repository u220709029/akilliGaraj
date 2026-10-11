package com.example.akilliGaraj.entities;


import com.example.akilliGaraj.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users2")
public class User2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Normalde 'name' olan alan Spring Security standartlarına uyması için 'username' yapıldı.
    private String username;

    private String password;

    // Veritabanına Enum'ın sayısal sırasının değil, doğrudan metin karşılığının (String) kaydedilmesi için
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}