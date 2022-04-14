package com.revature.lostchapterbackend.service;

import java.util.Collections;

import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
import com.revature.lostchapterbackend.model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LostChapterUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userRes;
        try {
            userRes = userService.getUserByUsername(username);
            if (userRes == null)
                throw new UsernameNotFoundException("Could not find user with username: " + username);
            else {
                return new org.springframework.security.core.userdetails.User(
                        username,
                        userRes.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            }
        } catch (InvalidParameterException e) {
            throw new UsernameNotFoundException("Null username not allowed");
        }
    }
}
