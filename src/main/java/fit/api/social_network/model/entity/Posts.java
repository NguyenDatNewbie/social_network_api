package fit.api.social_network.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Posts extends BaseEntity{
    @ManyToOne
    @Cascade(CascadeType.REMOVE)
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    private String image_url;
    private String caption;
    private Integer kind; // Posts & Stories
    private Integer likedAmount = 0;
    private Integer commentedAmount = 0;
}
