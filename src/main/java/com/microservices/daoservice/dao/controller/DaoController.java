package com.microservices.daoservice.dao.controller;

import com.microservices.daoservice.dao.enity.User;
import com.microservices.daoservice.dao.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dao")
public class DaoController {
    @Autowired
    IUserService userService;

    @PostMapping("/find-user")
    public User getUser(@RequestBody User user){
        return userService.findUser(user);
    }

    @PutMapping("/update-user")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}
