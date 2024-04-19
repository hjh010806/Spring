package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
    @GetMapping("/test")
    public String test
            () {
        return "test";
    }
}
