package com.scaler.user_service.controllers;

import com.scaler.user_service.dtos.SignupRequestDto;
import com.scaler.user_service.models.User;
import com.scaler.user_service.services.SelfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private SelfUserService selfUserService;

    @Autowired
    public UserController(SelfUserService selfUserService)
    {
        this.selfUserService = selfUserService;
    }

//    @GetMapping("/login")
//    public ResponseEntity<User> login(@RequestBody User user) throws UserDoesNotExistException {
//        return new ResponseEntity<>(selfUserService.login(user.getEmail(), user.getHashedPassword()), HttpStatus.OK);
//        //userService.login(user.getEmail(), user.getHashedPassword()),
//    }

    @PostMapping("/")
    public User signUp(@RequestBody SignupRequestDto signupRequestDto){
        String email = signupRequestDto.getEmail();
        String fullName = signupRequestDto.getName();
        String password = signupRequestDto.getPassword();
        return selfUserService.signUp(fullName, email, password);
    }

    @DeleteMapping()
    public ResponseEntity<Void> logout()
    {
        return null;
    }
}
