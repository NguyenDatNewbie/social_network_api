package fit.api.social_network.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Comments extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Posts post;
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Comments parent;
    private Integer kind;
    private String comment;
}
