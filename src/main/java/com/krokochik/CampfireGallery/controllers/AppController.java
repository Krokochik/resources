package com.krokochik.CampfireGallery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.IntStream;

@Controller
public class AppController {

    @GetMapping("/")
    public String main(Model model){
        return "main";
    }

}