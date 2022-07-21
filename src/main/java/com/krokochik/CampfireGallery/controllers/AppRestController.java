package com.krokochik.CampfireGallery.controllers;

import com.krokochik.CampfireGallery.models.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AppRestController {

    @Autowired
    private HttpServletRequest request;

    public static String devKey = AuthenticationManager.generateKey(40);
    public static AuthenticationManager authenticationManager;

    static{try{ authenticationManager = new AuthenticationManager(devKey); }catch(AuthenticationManager.KeySizeException ignored){}}

    @GetMapping("/console/{devKey}/{command}")
    public Map<String, String> consoleGenerateKey(@PathVariable("devKey") String key, @PathVariable String command, @RequestParam(required = false, name = "id") String idForRemove){
        HashMap<String, String> response = new HashMap<>();
        String resKey = null;
        short status = 503;
        Short id = null;
        boolean recognized = false;
        if (key.equals(devKey)){
            if(command.equalsIgnoreCase("addKey")) {
                recognized = true;
                resKey = AuthenticationManager.generateKey(authenticationManager.getKeyLength());
                try {
                    status = (short) (authenticationManager.addKey(resKey) ? 201 : 500);
                    id = authenticationManager.getLastKeyId();
                } catch (AuthenticationManager.KeySizeException keySizeException) {
                    status = 412;
                }
            }
            if(!recognized && command.equalsIgnoreCase("remove")){
                recognized = true;
                if(Short.parseShort(idForRemove) >= 0) {
                    if (Short.parseShort(idForRemove) <= authenticationManager.getLastKeyId()) {
                        authenticationManager.removeKey(Short.parseShort(idForRemove));
                        status = 200;
                    } else status = 404;
                }
                else status = 400;
            }
            if(!recognized && command.equalsIgnoreCase("removeAllUserKeys")){
                recognized = true;
                authenticationManager.removeAllUserKeys();
                status = 200;
            }
            if(!recognized && command.equalsIgnoreCase("print")){
                recognized = true;
                String[] keys = authenticationManager.getAllKeys();
                for(int i = 0; i < keys.length; i++){
                    response.put("id: " + i,"key: " + keys[i]);
                }
                status = 200;
            }
            if(!recognized && command.equalsIgnoreCase("help")){
                recognized = true;
                response.put("addKey", "Adds and issues an automatically generated key & its ID");
                response.put("remove", "Removes the key located at the address passed in the required id parameter");
                response.put("removeAllUserKeys", "Removes all keys except admin's" );
                response.put("print", "Prints all keys");
                status = 200;
            }
            if(!recognized)
                status = 400;
        }
        else status = 403;
        response.put("status", status + "");
        if(resKey != null)
            response.put("key", resKey);
        if(id != null){
            response.put("keyId", id + "");
        }
        return response;
    }

    @PostMapping("/post/{key}")
    public Map<String, String> postRequest(@PathVariable String key, @RequestBody String requestBody, @RequestHeader(name="Host") String host){
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