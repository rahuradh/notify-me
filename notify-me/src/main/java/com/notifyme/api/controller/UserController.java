package com.notifyme.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.notifyme.api.entity.UserEntity;
import com.notifyme.api.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addNewUser")
    public boolean saveUser(@RequestBody UserEntity userEntity) {
        return userService.addNewUser(userEntity);
    }

    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody UserEntity userEntity) {
        return userService.updateUser(userEntity);
    }

    @PostMapping("/deleteUser")
    public boolean deleteUser(@RequestBody UserEntity userEntity) {
        return userService.deleteUser(userEntity.getUserId());
    }

    @GetMapping("/getAllUsers")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserByUserName")
    public UserEntity getUserByUserName(@RequestParam String userName) {
        return userService.getUserByUserName(userName);
    }

    @GetMapping("/isUserNameExist")
    public boolean isUserNameExist(@RequestParam String userName) {
        return userService.isUserNameExist(userName);
    }

    @GetMapping("/isUserExist")
    public boolean isUserExist(@RequestParam String userName, @RequestParam String password) {
        return userService.isUserExist(userName, password);
    }
}
