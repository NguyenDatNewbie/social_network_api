package fit.api.social_network.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Likes extends BaseEntity {
    @ManyToOne
    @Cascade(CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @Cascade(CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Posts post;
    @ManyToOne
    @Cascade(CascadeType.REMOVE)
    @JoinColumn(name = "comment_id")
    private Comments comment;
    private Integer kind;
}
