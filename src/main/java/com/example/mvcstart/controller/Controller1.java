package com.example.mvcstart.controller;

import com.example.mvcstart.config.Token;
import com.example.mvcstart.config.Yml;
import com.example.mvcstart.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : KaelvihN
 * @date : 2023/9/8 14:26
 */
@Slf4j
@Controller
public class Controller1 {
    @GetMapping("/test1")
    public ModelAndView test1() {
        log.debug("test1()");
        return null;
    }

    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam("name") String name) {
        log.debug("test2({})", name);
        return null;
    }

    @PutMapping("/test3")
    public ModelAndView test3(@Token String token) {
        log.debug("test3({})", token);
        return null;
    }

    @RequestMapping("/test4")
    @Yml
    public User test4() {
        log.debug("test4");
        return new User("张三", 18);
    }
}

