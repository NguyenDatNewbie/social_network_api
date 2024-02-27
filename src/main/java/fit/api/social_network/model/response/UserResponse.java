package fit.api.social_network.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String name;
    private String email;
    private String bio;
    private String avatar;
}
