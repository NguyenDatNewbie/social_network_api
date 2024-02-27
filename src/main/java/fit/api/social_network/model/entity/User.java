package fit.api.social_network.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class User extends BaseEntity {
    @Column(unique = true)
    private String name;
    private String email;
    private String password;
    private String bio;
    private String avatar;
    private Integer kind;
    private String otp;
    private Date otpExpiredDate;
    private Date banedExpiredDate;
}
