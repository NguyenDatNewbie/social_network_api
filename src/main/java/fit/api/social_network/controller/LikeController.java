package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.criteria.FollowerCriteria;
import fit.api.social_network.model.criteria.LikeCriteria;
import fit.api.social_network.model.entity.Followers;
import fit.api.social_network.model.entity.Likes;
import fit.api.social_network.model.mapper.FollowerMapper;
import fit.api.social_network.model.mapper.LikeMapper;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.repository.FollowersRepository;
import fit.api.social_network.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private LikeMapper likesMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Likes like = likesRepository.findById(id).orElse(null);
            if (like == null) {
                throw new NotFoundException("Like Not Found");
            }
            apiResponse.ok(likesMapper.toResponse(like));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> listLikes(LikeCriteria likesCriteria, Pageable pageable) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Page<Likes> likePage = likesRepository.findAll(likesCriteria.getSpecification(), pageable);
            apiResponse.ok(likesMapper.toResponseList(likePage.getContent()), likePage.getTotalElements(), likePage.getTotalPages());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Likes like = likesRepository.findById(id).orElse(null);
            if (like == null) {
                throw new NotFoundException("Like Not Found");
            }
            likesRepository.deleteById(id);
            apiResponse.ok("Delete success");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}




