package fit.api.social_network.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class MessageViews extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="message_id", nullable = false)
    private Message message;
    @ManyToOne
    @JoinColumn(name="viewer_id", nullable = false)
    private User viewer;
}
