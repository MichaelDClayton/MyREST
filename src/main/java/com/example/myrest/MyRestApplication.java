package com.example.myrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class MyRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRestApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value="myName")  String myName){
        return "Hello "+myName;
    }

    @PostMapping("/create")
    public String updateRecord(@RequestBody String myName){
        return "Hello "+myName;
    }
}
