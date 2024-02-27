package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.exception.ValidationException;
import fit.api.social_network.model.request.LoginRequest;
import fit.api.social_network.model.request.RegisterRequest;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult){
        ApiResponse apiResponse;
        try {
            Map<String,String> result = authenticationService.register(registerRequest,bindingResult);
            apiResponse = new ApiResponse();
            apiResponse.ok(result);
            return ResponseEntity.ok(apiResponse);
        } catch (NotFoundException ex) {
            throw ex; // Rethrow NotFoundException
        } catch (ValidationException ex) {
            apiResponse = new ApiResponse();
            apiResponse.error(ex.getErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        ApiResponse apiResponse;
        try {
            Map<String,String> result = authenticationService.login(loginRequest,bindingResult);
            apiResponse = new ApiResponse();
            apiResponse.ok(result);
            return ResponseEntity.ok(apiResponse);
        } catch (ValidationException ex) {
            apiResponse = new ApiResponse();
            apiResponse.error(ex.getErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        } catch (ApplicationException ex) {
            apiResponse = new ApiResponse();
            apiResponse.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(apiResponse);
        }
    }
}
