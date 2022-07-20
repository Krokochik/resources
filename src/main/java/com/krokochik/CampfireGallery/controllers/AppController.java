package com.krokochik.CampfireGallery.controllers;

import com.krokochik.CampfireGallery.models.AuthenticationManager;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Controller
public class AppController {

    boolean init = true;

    static String devKey = AuthenticationManager.generateKey(20);
    public static AuthenticationManager authenticationManager;

    static{try{ authenticationManager = new AuthenticationManager(devKey); }catch(AuthenticationManager.KeySizeException ignored){}}

    @GetMapping("/")
    public String main(Model model){
        if(init) {
            System.out.println(devKey);
            init = false;
        }
        return "main";
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getRequests(){
        HashMap<String, String> response = new HashMap<>();
        response.put("value", "1");
        return response;
    }

    static class JsonResponse{
        String response;
        JsonResponse(String str){
            response = str;
        }
    }
}