package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.criteria.PostCriteria;
import fit.api.social_network.model.entity.Posts;
import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.mapper.PostMapper;
import fit.api.social_network.model.request.post.CreatePostRequest;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.model.response.StatusEnum;
import fit.api.social_network.repository.PostsRepository;
import fit.api.social_network.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController extends AbasicMethod{
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private final PostMapper postsMapper;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Posts post = postsRepository.findById(id).orElse(null);
            if (post == null) {
                throw new NotFoundException("Post Not Found");
            }
            apiResponse.ok(postsMapper.toResponse(post));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> listPosts(PostCriteria postsCriteria, Pageable pageable) {
        ApiResponse apiResponse = new ApiResponse();
        Page<Posts> postPage = postsRepository.findAll(postsCriteria.getSpecification(), pageable);
        apiResponse.ok(postsMapper.toResponseList(postPage.getContent()), postPage.getTotalElements(), postPage.getTotalPages());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        System.out.println(userId);
        ApiResponse apiResponse = new ApiResponse();
        Posts post = postsRepository.findById(id).orElse(null);
        if (post == null) {
            throw new NotFoundException("Post Not Found");
        }
        postsRepository.deleteById(id);
        apiResponse.ok("Delete success");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreatePostRequest createPostRequest, BindingResult bindingResult) {
        ApiResponse apiResponse = new ApiResponse();
        User user = userRepository.findById(getCurrentUserId()).orElse(null);
        if(user == null){
            apiResponse.error("User not found");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        Posts posts = postsMapper.createFromRequest(createPostRequest);
        posts.setUser(user);
        postsRepository.save(posts);
        apiResponse.ok("Create success");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}








