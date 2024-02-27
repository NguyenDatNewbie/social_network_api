package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.mapper.UserMapper;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.model.response.UserResponse;
import fit.api.social_network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserRepository userRepository;
    private final UserMapper userMapper;
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id){
        ApiResponse apiResponse;
        try {
            apiResponse = new ApiResponse();
            User user = userRepository.findById(id).orElse(null);
            if(user==null){
                throw new NotFoundException("User Not Found");
            }
            apiResponse.ok(userMapper.toResponse(user));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            apiResponse = new ApiResponse();
            apiResponse.error(ex.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException(); // Handle other exceptions
        }
    }
}
