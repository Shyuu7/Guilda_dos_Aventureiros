package br.com.infnet.dr1tp1.entity.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "organizacoes", schema = "audit")
@Getter
@Setter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome", nullable = false, length = 120)
    private String name;

    @Column(name="ativo", nullable = false)
    private Boolean isActive;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
