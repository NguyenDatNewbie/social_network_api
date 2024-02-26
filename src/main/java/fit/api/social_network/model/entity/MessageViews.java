package fit.api.social_network.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class MessageViews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_view_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="message_id", nullable = false)
    private Message message;

    @ManyToOne
    @JoinColumn(name="viewer_id", nullable = false)
    private User viewer;

    private Date createdDate;
    private Date modifiedDate;
    private Integer status;
}
