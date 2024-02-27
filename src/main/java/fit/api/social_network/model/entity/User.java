package fit.api.social_network.model.entity;

import fit.api.social_network.model.enums.ROLES;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class User extends BaseEntity implements UserDetails {
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
    @Enumerated(EnumType.STRING)
    private ROLES role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
