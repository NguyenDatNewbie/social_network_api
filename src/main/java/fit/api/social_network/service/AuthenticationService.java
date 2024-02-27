package fit.api.social_network.service;

import fit.api.social_network.model.request.LoginRequest;
import fit.api.social_network.model.request.RegisterRequest;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface AuthenticationService {
    Map<String, String> register(RegisterRequest registerRequest, BindingResult bindingResult);

    Map<String, String> login(LoginRequest loginRequest, BindingResult bindingResult);
}
