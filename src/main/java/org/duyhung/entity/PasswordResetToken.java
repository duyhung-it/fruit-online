package org.duyhung.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetToken implements Serializable {
    private static final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_RESET_TOKEN"))
    private User user;
    private Date expirationTime;
    public PasswordResetToken(User user,String token){
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationDate();
    }
    public PasswordResetToken(String token){
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate();
    }

    private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, PasswordResetToken.EXPIRATION_TIME);
        return new Date((calendar.getTime().getTime()));
    }
}
