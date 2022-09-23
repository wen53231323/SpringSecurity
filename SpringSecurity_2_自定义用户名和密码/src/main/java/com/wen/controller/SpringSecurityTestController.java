package com.wen.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSecurityTestController {
    @GetMapping("/test")
    public String test() {
        return "Test";
    }
}
