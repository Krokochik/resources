package com.krokochik.CampfireGallery.controllers;

import com.krokochik.CampfireGallery.models.AuthenticationManager;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@RestController
@Controller
public class AppController {

    boolean init = true;

    public static String devKey = AuthenticationManager.generateKey(20);
    public static AuthenticationManager authenticationManager;

    static{try{ authenticationManager = new AuthenticationManager(devKey); }catch(AuthenticationManager.KeySizeException ignored){}}

    @GetMapping("/")
    public String mainGetRequest(Model model){
        return "main";
    }

    @GetMapping("/console/{devKey}/generateKey")
    public Map<String, String> consoleGenerateKey(@PathVariable("devKey") String key){
        HashMap<String, String> response = new HashMap<>();
        String resKey = null;
        short status;
        if (key.equals(devKey)){
            resKey = AuthenticationManager.generateKey(authenticationManager.getKeyLength());
            try {
                status = (short) (authenticationManager.addKey(resKey) ? 200 : 500);
            } catch (AuthenticationManager.KeySizeException keySizeException){ status = 412; }
        }
        else status = 403;
        response.put("status", status + "");
        if(resKey != null)
            response.put("key", resKey);
        return response;
    }

    @PostMapping("/post/{key}")
    public Map<String, String> postRequest(@PathVariable String key, String requestBody){
        HashMap<String, String> response = new HashMap<>();
        short status;
        if(authenticationManager.isExist(key)){
            response.put("requestBody", requestBody);
            status = 200;
        }
        else status = 401;
        response.put("status", status + "");
        return response;
    }
}