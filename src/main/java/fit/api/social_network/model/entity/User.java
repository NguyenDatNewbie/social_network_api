package fit.api.social_network.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String name;

    private String email;
    private String password;
    private String bio;
    private String avatar;
    private Integer kind;
    private String otp;
    private Date otpExpiredDate;
    private Date createdDate;
    private Date modifiedDate;
    private Date banedExpiredDate;
    private Integer status;
}
