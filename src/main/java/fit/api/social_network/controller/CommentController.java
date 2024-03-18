package fit.api.social_network.controller;

import fit.api.social_network.constant.SocialConstant;
import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.criteria.BlockCriteria;
import fit.api.social_network.model.criteria.CommentCriteria;
import fit.api.social_network.model.entity.*;
import fit.api.social_network.model.mapper.BlockMapper;
import fit.api.social_network.model.mapper.CommentMapper;
import fit.api.social_network.model.request.comment.CreateCommentRequest;
import fit.api.social_network.model.request.like.CreateLikeRequest;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.repository.BlockRepository;
import fit.api.social_network.repository.CommentsRepository;
import fit.api.social_network.repository.PostsRepository;
import fit.api.social_network.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController extends AbasicMethod{
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private CommentMapper commentsMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostsRepository postsRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Comments comment = commentsRepository.findById(id).orElse(null);
            if (comment == null) {
                throw new NotFoundException("Comment Not Found");
            }
            apiResponse.ok(commentsMapper.toResponse(comment));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> listComments(CommentCriteria commentsCriteria, Pageable pageable) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Page<Comments> commentPage = commentsRepository.findAll(commentsCriteria.getSpecification(), pageable);
            apiResponse.ok(commentsMapper.toResponseList(commentPage.getContent()), commentPage.getTotalElements(), commentPage.getTotalPages());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Comments comment = commentsRepository.findById(id).orElse(null);
            if (comment == null) {
                throw new NotFoundException("Comment Not Found");
            }
            commentsRepository.deleteById(id);
            apiResponse.ok("Delete success");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateCommentRequest createCommentRequest, BindingResult bindingResult) {
        ApiResponse apiResponse = new ApiResponse();
        User user = userRepository.findById(getCurrentUserId()).orElse(null);
        if(user == null){
            apiResponse.error("User not found");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        Posts post = postsRepository.findById(createCommentRequest.getPostId()).orElse(null);
        if(post == null){
            apiResponse.error("Post not found");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        Comments comment = commentsMapper.createFromCreateRequest(createCommentRequest);
        comment.setUser(user);
        comment.setPost(post);
        if(createCommentRequest.getKind().equals(SocialConstant.COMMENT_KIND_COMMENT)){
            if(createCommentRequest.getCommentId() == null){
                apiResponse.error("Comment kind comment not allow null commentId ");
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }
            Comments parent = commentsRepository.findById(createCommentRequest.getCommentId()).orElse(null);
            if(parent == null){
                apiResponse.error("Comment not found");
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }
            comment.setParent(parent);
        }
        post.setCommentedAmount(post.getCommentedAmount()+1);
        commentsRepository.save(comment);
        postsRepository.save(post);
        apiResponse.ok("Create success");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}


