package com.sda.demo.controller;

import com.sda.demo.common.util.AuthenticationBean;
import com.sda.demo.dto.AuthTokenDto;
import com.sda.demo.dto.Files.ResponseFile;
import com.sda.demo.dto.Files.ResponseMessage;
import com.sda.demo.dto.UserDto;
import com.sda.demo.persitance.model.PhotoU;
import com.sda.demo.persitance.model.UserModel;
import com.sda.demo.repository.UserRepository;
import com.sda.demo.service.PhotoUService;
import com.sda.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoUService photoUService;


    @PostMapping("/user")
    public void addUser(@RequestBody UserDto userDto) {
        userService.save(userDto);

    }
    @PostMapping("/login/{username}/{password}")
    public AuthTokenDto login(@PathVariable(name = "username") String username, @PathVariable(name = "password") String password) throws AccessDeniedException {
        return userService.login(username,password);
    }
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/user")
    public List<UserDto> getUser() {
        return userService.findALl();
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable(name = "id") Long id) {
        return userService.findById(id);
    }

    @PutMapping("/user")
    public void update(@RequestBody UserDto userDto) {
        userService.update(userDto);

    }

    @PostMapping("/photos")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("photo") MultipartFile photo) {
        String message;
        try {

            photoUService.store(photo);

            message = "Uploaded the file successfully: " + photo.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + photo.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @RequestMapping(value = "/photos", method = RequestMethod.GET)
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = photoUService.getAllphotos().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/photos/")
                    .path(dbFile.getId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<List<ResponseFile>> getUserPhoto(@PathVariable(name = "id") Long id) {
        List<ResponseFile> files = photoUService.getUserPhoto(id).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/photos/")
                    .path(dbFile.getId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        PhotoU photoU = photoUService.getPhoto(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photoU.getName() + "\"")
                .body(photoU.getData());
    }

    @GetMapping(path = "/basicauth")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated");
    }
    @GetMapping("/user/getbyusername/{username}")
    public UserDto getUserByUsername(@PathVariable(name = "username") String userName){
        UserDto userDto = userService.findByUsername(userName);
        return userDto;
    }
}
