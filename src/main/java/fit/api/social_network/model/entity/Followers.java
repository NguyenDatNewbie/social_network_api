package fit.api.social_network.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Followers extends BaseEntity{
    @ManyToOne
    @Cascade(CascadeType.REMOVE)
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    @ManyToOne
    @Cascade(CascadeType.REMOVE)
    @JoinColumn(name="following_user_id")
    private User followingUser;
}
