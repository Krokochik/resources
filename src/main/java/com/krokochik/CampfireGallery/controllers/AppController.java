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

    static String devKey = AuthenticationManager.generateKey(20);
    public static AuthenticationManager authenticationManager;

    static{try{ authenticationManager = new AuthenticationManager(devKey); }catch(AuthenticationManager.KeySizeException ignored){}}

    @GetMapping("/")
    public String main(Model model){
        if (init) {
            System.out.println(devKey);
            init = false;
        }
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

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getRequests(){
        HashMap<String, String> response = new HashMap<>();
        response.put("value", "1");
        return response;
    }
}