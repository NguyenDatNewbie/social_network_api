package fit.api.social_network.model.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Room extends BaseEntity{
    private String name;
    //    message string Sao lai them
}
