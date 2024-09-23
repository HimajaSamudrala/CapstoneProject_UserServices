package com.scaler.user_service.controllers;

import com.scaler.user_service.dtos.LoginRequestDto;
import com.scaler.user_service.dtos.LogoutRequestDto;
import com.scaler.user_service.dtos.SignupRequestDto;
import com.scaler.user_service.dtos.UserDto;
import com.scaler.user_service.exceptions.PasswordNotMatchesException;
import com.scaler.user_service.exceptions.TokenNotExistOrAlreadyExpired;
import com.scaler.user_service.exceptions.UserDoesNotExistException;
import com.scaler.user_service.models.Token;
import com.scaler.user_service.models.User;
import com.scaler.user_service.repositories.UserRepository;
import com.scaler.user_service.services.SelfUserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private SelfUserService selfUserService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(SelfUserService selfUserService,
                          UserRepository userRepository)
    {
        this.selfUserService = selfUserService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto) throws UserDoesNotExistException, PasswordNotMatchesException {
        return selfUserService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupRequestDto signupRequestDto){
        String email = signupRequestDto.getEmail();
        String fullName = signupRequestDto.getName();
        String password = signupRequestDto.getPassword();
        return UserDto.fromUser(selfUserService.signUp(fullName, email, password));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws TokenNotExistOrAlreadyExpired
    {
        selfUserService.logout(logoutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") @NonNull String token) throws TokenNotExistOrAlreadyExpired {
        return UserDto.fromUser(selfUserService.validateToken(token));
    }
}
