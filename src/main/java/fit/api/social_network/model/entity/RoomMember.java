package fit.api.social_network.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class RoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    private Date createdDate;
    private Date modifiedDate;
    private Integer status;

}
