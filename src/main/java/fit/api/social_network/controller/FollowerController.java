package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.criteria.CommentCriteria;
import fit.api.social_network.model.criteria.FollowerCriteria;
import fit.api.social_network.model.entity.Comments;
import fit.api.social_network.model.entity.Followers;
import fit.api.social_network.model.mapper.CommentMapper;
import fit.api.social_network.model.mapper.FollowerMapper;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.repository.CommentsRepository;
import fit.api.social_network.repository.FollowersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
public class FollowerController {
    @Autowired
    private FollowersRepository followersRepository;
    @Autowired
    private FollowerMapper followersMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Followers follower = followersRepository.findById(id).orElse(null);
            if (follower == null) {
                throw new NotFoundException("Follower Not Found");
            }
            apiResponse.ok(followersMapper.toResponse(follower));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> listFollowers(FollowerCriteria followersCriteria, Pageable pageable) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Page<Followers> followerPage = followersRepository.findAll(followersCriteria.getSpecification(), pageable);
            apiResponse.ok(followersMapper.toResponseList(followerPage.getContent()), followerPage.getTotalElements(), followerPage.getTotalPages());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Followers follower = followersRepository.findById(id).orElse(null);
            if (follower == null) {
                throw new NotFoundException("Follower Not Found");
            }
            followersRepository.deleteById(id);
            apiResponse.ok("Delete success");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}



