package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.criteria.PostCriteria;
import fit.api.social_network.model.entity.Likes;
import fit.api.social_network.model.entity.Posts;
import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.mapper.PostMapper;
import fit.api.social_network.model.request.post.CreatePostRequest;
import fit.api.social_network.model.request.post.UpdatePostRequest;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.model.response.post.PostResponse;
import fit.api.social_network.repository.LikesRepository;
import fit.api.social_network.repository.PostsRepository;
import fit.api.social_network.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private LikesRepository likesRepository;
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getById(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            User user = userRepository.findById(getCurrentUserId()).orElse(null);
            if(user == null){
                throw new NotFoundException("User not found");
            }
            Posts post = postsRepository.findById(id).orElse(null);
            if (post == null) {
                throw new NotFoundException("Post Not Found");
            }
            PostResponse postResponse = postsMapper.toResponse(post);
            setIsLike(postResponse,user.getId());
            apiResponse.ok(postResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
    void setIsLike(PostResponse postResponse, Long userId){
        Likes like = likesRepository.findFirstByPostIdAndUserId(postResponse.getId(),userId);
        if(like!=null){
            postResponse.setIsLiked(true);
        }
    }


    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<PostResponse>>> listPosts(PostCriteria postsCriteria, Pageable pageable) {
        ApiResponse apiResponse = new ApiResponse();
        User user = userRepository.findById(getCurrentUserId()).orElse(null);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        Page<Posts> postPage = postsRepository.findAll(postsCriteria.getSpecification(), pageable);
        List<PostResponse> postResponseList = postsMapper.toResponseList(postPage.getContent());
        for (PostResponse postResponse: postResponseList){
            setIsLike(postResponse,user.getId());
        }
        apiResponse.ok(postResponseList, postPage.getTotalElements(), postPage.getTotalPages());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        ApiResponse apiResponse = new ApiResponse();
        User user = userRepository.findById(getCurrentUserId()).orElse(null);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        Posts post = postsRepository.findById(id).orElse(null);
        if (post == null) {
            throw new NotFoundException("Post Not Found");
        }
        postsRepository.deleteById(id);
        apiResponse.ok("Delete success");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody CreatePostRequest createPostRequest, BindingResult bindingResult) {
        ApiResponse apiResponse = new ApiResponse();
        User user = userRepository.findById(getCurrentUserId()).orElse(null);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        Posts posts = postsMapper.createFromRequest(createPostRequest);
        posts.setUser(user);
        postsRepository.save(posts);
        apiResponse.ok("Create success");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody UpdatePostRequest updatePostRequest, BindingResult bindingResult) {
        ApiResponse apiResponse = new ApiResponse();
        User user = userRepository.findById(getCurrentUserId()).orElse(null);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        Posts posts = postsRepository.findFirstByIdAndUserId(updatePostRequest.getPostId(),user.getId());
        if(user == null){
            throw new NotFoundException("Post not found");
        }
        postsMapper.updateFromUpdateRequest(updatePostRequest,posts);
        postsRepository.save(posts);
        apiResponse.ok("Update success");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}








