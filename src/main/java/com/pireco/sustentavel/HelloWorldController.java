package com.pireco.sustentavel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloworld")
public class HelloWorldController {

    @GetMapping
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World!");
    }
}
