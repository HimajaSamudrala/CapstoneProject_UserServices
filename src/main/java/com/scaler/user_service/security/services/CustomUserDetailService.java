package com.scaler.user_service.security.services;

import com.scaler.user_service.models.User;
import com.scaler.user_service.repositories.UserRepository;
import com.scaler.user_service.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findUserByEmail(username);

        if(username.isEmpty())
        {
            throw new UsernameNotFoundException("User with email " + username + "not found");
        }

        CustomUserDetails customUserDetails= new CustomUserDetails(userOptional.get());
        return customUserDetails;
    }
}
