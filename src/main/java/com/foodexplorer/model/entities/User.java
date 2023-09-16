package com.foodexplorer.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Entity(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "nome")
    private String name;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(name = "senha")
    @Size(min = 8, max = 20)
    private String password;

    @Column(name = "url_foto_perfil")
    private String photoProfileUrl;

    @NotNull
    @Column(name = "admin")
    private Boolean isAdmin = false;

    @CreationTimestamp()
    @Column(name = "criado_em")
    private Date createadAt;

    @UpdateTimestamp
    @Column(name = "ultima_atualizacao")
    private Date updateAt;

    public User() {
//        this.name = name;
//        this.email = email;
//        this.password = hashedPassword;
    }
}