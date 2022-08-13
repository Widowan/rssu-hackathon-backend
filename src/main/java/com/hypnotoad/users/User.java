package com.hypnotoad.users;

import com.hypnotoad.auth.token.Token;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(
        name = "users_id_seq",
        sequenceName = "users_id_seq",
        allocationSize = 1)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String avatar;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Token token;
}
