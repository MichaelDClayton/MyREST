package com.example.myrest.repository;

import com.example.myrest.model.MyUser;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomListCrudRepository extends ListCrudRepository<MyUser, Long> {

}
