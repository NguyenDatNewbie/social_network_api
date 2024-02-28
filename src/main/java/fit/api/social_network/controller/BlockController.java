package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.criteria.BlockCriteria;
import fit.api.social_network.model.criteria.UserCriteria;
import fit.api.social_network.model.entity.Block;
import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.mapper.BlockMapper;
import fit.api.social_network.model.mapper.UserMapper;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.repository.BlockRepository;
import fit.api.social_network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/block")
@RequiredArgsConstructor
public class BlockController {
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private BlockMapper blockMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Block block = blockRepository.findById(id).orElse(null);
            if (block == null) {
                throw new NotFoundException("Block Not Found");
            }
            apiResponse.ok(blockMapper.toResponse(block));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> listBlocks(BlockCriteria blockCriteria, Pageable pageable) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Page<Block> blockPage = blockRepository.findAll(blockCriteria.getSpecification(), pageable);
            apiResponse.ok(blockMapper.toResponseList(blockPage.getContent()), blockPage.getTotalElements(), blockPage.getTotalPages());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Block block = blockRepository.findById(id).orElse(null);
            if (block == null) {
                throw new NotFoundException("Block Not Found");
            }
            blockRepository.deleteById(id);
            apiResponse.ok("Delete success");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}

