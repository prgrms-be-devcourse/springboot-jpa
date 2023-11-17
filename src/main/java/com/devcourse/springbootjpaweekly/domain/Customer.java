package com.devcourse.springbootjpaweekly.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
