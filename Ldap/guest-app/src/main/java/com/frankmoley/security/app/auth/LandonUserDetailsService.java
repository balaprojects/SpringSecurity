package com.frankmoley.security.app.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandonUserDetailsService  implements UserDetailsService{

    private final UserRepository userRepository;
    private final AuthUserGroupRepository authUserGroupRepository;

    public LandonUserDetailsService(UserRepository userRepository,
                                    AuthUserGroupRepository authUserGroupRepository){
        super();
        this.userRepository = userRepository;
        this.authUserGroupRepository = authUserGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if(null==user){
            throw new UsernameNotFoundException("cannot find username: " + username);
        }
        List<AuthUserGroup> authUserGroups = this.authUserGroupRepository.findByUsername(username);
        return new LandonUserPrincipal(user, authUserGroups);
    }
}
