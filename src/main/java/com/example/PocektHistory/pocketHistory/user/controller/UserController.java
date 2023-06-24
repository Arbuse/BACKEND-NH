package com.example.PocektHistory.pocketHistory.user.controller;


import com.example.PocektHistory.pocketHistory.user.entity.Authorization;
import com.example.PocektHistory.pocketHistory.user.entity.User;
import com.example.PocektHistory.pocketHistory.user.models.UserEditApi;
import com.example.PocektHistory.pocketHistory.user.models.UserRegister;
import com.example.PocektHistory.pocketHistory.user.service.UserCreateService;
import com.example.PocektHistory.pocketHistory.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCreateService userCreateService;



    @GetMapping("/userById")
    public User getUserById(@RequestParam Long userId) throws ExecutionException, InterruptedException{
        return userService.getUserById(userId);
    }
    @GetMapping("/getUsers")
    public List<User> getUsers() throws  ExecutionException, InterruptedException{
        return userService.getUsers();
    }

    @PostMapping("/createUser")
    public void createUser(@RequestBody UserRegister userRegister) throws Exception {
        userCreateService.createUser(userRegister);
    }

    @PostMapping("/updateUser")
    public void updateUser(@RequestBody UserEditApi userEditApi)throws  ExecutionException, InterruptedException{
        userService.updateUser(userEditApi);
    }

    @GetMapping("/authorization")
    public Long authorization(@RequestParam String password, @RequestParam String email) {
        return userService.getAuthorization(password, email);
    }

    @GetMapping("/ranking")
    public List<User> ranking() throws ExecutionException, InterruptedException {
        return userService.ranking();
    }

    @PostMapping("/addPoints")
    public void addPoints(@RequestParam Long userId, @RequestParam Integer points) throws ExecutionException, InterruptedException {
        userService.addPoints(userId, points);
    }

    @PostMapping("/addGames")
    public void addGames(@RequestParam Long userId) throws ExecutionException, InterruptedException {
        userService.addGames(userId);
    }

    @PostMapping("/reset/")
    public void resetLearningMode(@RequestParam Long userId) {

        userService.resetLearningMode(userId);
    }

}

