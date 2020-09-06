package com.frankmoley.security.app.service;

import com.frankmoley.security.app.auth.Principal;
import com.frankmoley.security.app.auth.User;
import com.frankmoley.security.app.auth.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalService implements UserDetailsService {

    private UserRepository userRepository;

    public PrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new Principal(user);
    }
}
