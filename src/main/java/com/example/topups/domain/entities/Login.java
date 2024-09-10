package com.example.topups.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "logins")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Login(String username, String password, User user) {
        this.username = username;
        this.password = password;
        this.user = user;
    }

}
