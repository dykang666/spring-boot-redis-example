package com.example.springboot.redis.cache.controller;

import com.example.springboot.redis.cache.entity.User;
import com.example.springboot.redis.cache.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "inserOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public User insertOrUpdate(@RequestBody User user){
        final User user1 = userService.saveOrUpdate(user);
        return user1;
    }

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    @ResponseBody
    public User getUser( Long userId){
        final User user1 = userService.get(userId);
        return user1;
    }
}
