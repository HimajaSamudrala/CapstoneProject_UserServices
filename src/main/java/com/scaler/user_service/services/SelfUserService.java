package com.scaler.user_service.services;

import com.scaler.user_service.exceptions.PasswordNotMatchesException;
import com.scaler.user_service.exceptions.UserDoesNotExistException;
import com.scaler.user_service.models.Token;
import com.scaler.user_service.models.User;
import com.scaler.user_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class SelfUserService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SelfUserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User signUp(String fullName, String email, String password)
    {
        User u = new User();
        u.setName(fullName);
        u.setEmail(email);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));
        User user = userRepository.save(u);
        //Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        return user;
    }

    public Token login(String email, String password) throws UserDoesNotExistException, PasswordNotMatchesException
    {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if(userOptional.isEmpty())
        {
            throw new UserDoesNotExistException("User does not exist");
        }
        User user =userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword()))
        {
            throw new PasswordNotMatchesException("Password doesn't match");
        }

        Token token = new Token();
        token.setUser(user);
        //token.setExpiryAt();
        return null;
    }

}
