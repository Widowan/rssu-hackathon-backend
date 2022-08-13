package com.hypnotoad.auth.token;

import com.hypnotoad.configurations.customTypes.IntegerTimestampConverter;
import com.hypnotoad.users.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usertokens")
public class Token {
    @Id
    @Column(nullable = false, length = 32)
    private String token;

    @Column(nullable = false, name = "expiry_time")
    @Convert(converter = IntegerTimestampConverter.class)
    private LocalDateTime expiryTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}