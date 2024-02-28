package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.criteria.BlockCriteria;
import fit.api.social_network.model.criteria.CommentCriteria;
import fit.api.social_network.model.entity.Block;
import fit.api.social_network.model.entity.Comments;
import fit.api.social_network.model.mapper.BlockMapper;
import fit.api.social_network.model.mapper.CommentMapper;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.repository.BlockRepository;
import fit.api.social_network.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private CommentMapper commentsMapper;

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
}


