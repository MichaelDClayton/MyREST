package com.example.myrest.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = Optional.ofNullable(myUserRepository.findByUsername(username));
        if(myUser.isPresent()) {
            var userObj = myUser.get();
            return User.builder()
                    .password(userObj.getPassword())
                    .username(userObj.getUsername())
                    .roles(userObj.getRoles(userObj))
                    .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(MyUser myUser) {
        if(myUser.getRoles(myUser) != null) {
            return new String[]{"USER"};
        }
        return myUser.getRoles(myUser).split(",");
    }
}
