package fit.api.social_network.controller;

import fit.api.social_network.exception.ApplicationException;
import fit.api.social_network.exception.NotFoundException;
import fit.api.social_network.model.criteria.PostCriteria;
import fit.api.social_network.model.criteria.RoomCriteria;
import fit.api.social_network.model.entity.Posts;
import fit.api.social_network.model.entity.Room;
import fit.api.social_network.model.mapper.PostMapper;
import fit.api.social_network.model.mapper.RoomMapper;
import fit.api.social_network.model.response.ApiResponse;
import fit.api.social_network.repository.PostsRepository;
import fit.api.social_network.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomMapper roomMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Room room = roomRepository.findById(id).orElse(null);
            if (room == null) {
                throw new NotFoundException("Room Not Found");
            }
            apiResponse.ok(roomMapper.toResponse(room));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> listRooms(RoomCriteria roomCriteria, Pageable pageable) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Page<Room> roomPage = roomRepository.findAll(roomCriteria.getSpecification(), pageable);
            apiResponse.ok(roomMapper.toResponseList(roomPage.getContent()), roomPage.getTotalElements(), roomPage.getTotalPages());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        try {
            ApiResponse apiResponse = new ApiResponse();
            Room room = roomRepository.findById(id).orElse(null);
            if (room == null) {
                throw new NotFoundException("Room Not Found");
            }
            roomRepository.deleteById(id);
            apiResponse.ok("Delete success");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }
}









