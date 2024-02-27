package fit.api.social_network.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Message extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="room_id", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name="sender_id", nullable = false)
    private User sender;
    private String message;
}
