package com.sda.demo.controller;

import com.sda.demo.dto.UserDto;
import com.sda.demo.repository.UserRepository;
import com.sda.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/user")
    public void addUser(@RequestBody UserDto userDto) {
        userService.save(userDto);

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
        userService.save(userDto);

    }
}
