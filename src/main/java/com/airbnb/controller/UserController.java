package com.airbnb.controller;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.dto.TokenResponse;
import com.airbnb.entity.PropertyUser;
import com.airbnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/u")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyDto){
        PropertyUser propertyUser = userService.addUser(propertyDto);
        if(propertyUser!=null){
            return new ResponseEntity<>("Registration is Sucessful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/addPropertyOwner")
    public ResponseEntity<String> addPropertyOwner(@RequestBody PropertyUserDto propertyDto){
        PropertyUser propertyUser = userService.addUser(propertyDto);
        if(propertyUser!=null){
            return new ResponseEntity<>("Registration is Sucessful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/login")
    public ResponseEntity<?>  login(@RequestBody LoginDto loginDto){
       String token=userService.verifyLogin(loginDto);
       if(token!=null) {
           TokenResponse tokenResponse = new TokenResponse();
           tokenResponse.setToken(token); //As we want to response JSON Entity.
           return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
       }
       return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/profile")
    public PropertyUser getCurrectUserProfile(@AuthenticationPrincipal PropertyUser user){
        return user;
    }



}
