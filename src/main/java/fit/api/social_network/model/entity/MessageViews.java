package fit.api.social_network.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MessageViews extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="message_id", nullable = false)
    private Message message;
    @ManyToOne
    @JoinColumn(name="viewer_id", nullable = false)
    private User viewer;
}
