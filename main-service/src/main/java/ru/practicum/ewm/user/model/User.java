package ru.practicum.ewm.user.model;

import lombok.*;

import javax.persistence.*;

/**
 * Пользователь
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Column(name = "user_name", unique = true, nullable = false, length = 100)
    private String name;
    @Column(name = "user_email", unique = true, nullable = false, length = 50)
    private String email;
}
