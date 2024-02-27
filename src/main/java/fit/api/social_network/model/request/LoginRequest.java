package fit.api.social_network.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotEmpty(message = "Email is required!")
    @Email(message = "Email incorrect format")
    private String email;
    @NotEmpty(message = "Password is required!")
    private String password;
}
