package com.example.myrest.repository;

import com.example.myrest.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUsername(String username);

}
