package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.criteria.NotificationCriteria;
import fit.api.social_network.model.criteria.PostCriteria;
import fit.api.social_network.model.entity.Notifications;
import fit.api.social_network.model.entity.Posts;
import fit.api.social_network.model.mapper.NotificationMapper;
import fit.api.social_network.model.mapper.PostMapper;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.repository.NotificationsRepository;
import fit.api.social_network.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private final PostMapper postsMapper;

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
        try {
            ApiResponse apiResponse = new ApiResponse();
            Page<Posts> postPage = postsRepository.findAll(postsCriteria.getSpecification(), pageable);
            apiResponse.ok(postsMapper.toResponseList(postPage.getContent()), postPage.getTotalElements(), postPage.getTotalPages());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Posts post = postsRepository.findById(id).orElse(null);
            if (post == null) {
                throw new NotFoundException("Post Not Found");
            }
            postsRepository.deleteById(id);
            apiResponse.ok("Delete success");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}








