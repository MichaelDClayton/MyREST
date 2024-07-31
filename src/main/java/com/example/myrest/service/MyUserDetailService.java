package com.example.myrest.service;

import com.example.myrest.model.MyUser;
import com.example.myrest.repository.CustomListCrudRepository;
import com.example.myrest.repository.MyUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MyUserDetailService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    private final CustomListCrudRepository customListCrudRepository;

    public MyUserDetailService(MyUserRepository myUserRepository,CustomListCrudRepository customListCrudRepository){
        this.customListCrudRepository = customListCrudRepository;
        this.myUserRepository = myUserRepository;
    }
    
    public UserDetails findUserById(@PathVariable Long id) {
        var myUser = customListCrudRepository.findById(id);
        if(myUser.isPresent()){
            return User.builder()
                    .username(myUser.get().getUsername())
                    .password(myUser.get().getPassword())
                    .roles(getRoles(myUser.get()))
                    .build();
        }else{
            throw new UsernameNotFoundException("Invalid User ID");
        }
    }

    public List<UserDetails> loadAllUsers(){
        List<MyUser> myUsers = customListCrudRepository.findAll();
        List<UserDetails> userDetailsList = new ArrayList<>();
        for(MyUser myUser : myUsers){
            UserDetails userDetails = User.builder()
                    .username(myUser.getUsername())
                    .password(myUser.getPassword())
                    .roles(getRoles(myUser))
                    .build();
            userDetailsList.add(userDetails);
        }
        return  userDetailsList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = Optional.ofNullable(myUserRepository.findByUsername(username));
        if(myUser.isPresent()) {
            var userObj = myUser.get();
            return User.builder()
                    .password(userObj.getPassword())
                    .username(userObj.getUsername())
                    .roles(getRoles(userObj))
                    .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(MyUser myUser) {
        if(myUser.getRole() == null) {
            return new String[]{"USER"};
        }
        return myUser.getRole().split(",");
    }
}
