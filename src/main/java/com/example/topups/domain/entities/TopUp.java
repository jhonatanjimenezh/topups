package com.example.topups.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topups")
public class TopUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDateTime date;

    private String numberPhone;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "operator_id")
    private Long operatorId;
}
