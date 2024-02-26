package fit.api.social_network.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Posts post;

    @ManyToOne
    @JoinColumn(name = "comment_id",nullable = false)
    private Comments comment;

    private Integer kind;
    private Date createdDate;
    private Date modifiedDate;
    private Integer status;
}
