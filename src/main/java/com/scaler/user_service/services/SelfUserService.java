package com.scaler.user_service.services;

import com.scaler.user_service.exceptions.PasswordNotMatchesException;
import com.scaler.user_service.exceptions.TokenNotExistOrAlreadyExpired;
import com.scaler.user_service.exceptions.UserDoesNotExistException;
import com.scaler.user_service.models.Token;
import com.scaler.user_service.models.User;
import com.scaler.user_service.repositories.TokenRepository;
import com.scaler.user_service.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class SelfUserService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private TokenRepository tokenRepository;

    @Autowired
    public SelfUserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
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

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newDateTime = now.plus(30, ChronoUnit.DAYS);
        Date expiryDate = Date.from(newDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Token savedToken = tokenRepository.save(token);

        return savedToken;
    }

    public void logout(String token) throws TokenNotExistOrAlreadyExpired
    {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeleted(token, false);
        if(tokenOptional.isEmpty())
        {
            throw new TokenNotExistOrAlreadyExpired("Session expired");
        }

        Token t = tokenOptional.get();
        t.setDeleted(true);
        tokenRepository.save(t);
    }
}
