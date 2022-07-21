package com.krokochik.CampfireGallery.models;

import java.util.Base64;

public class Cryptographer {
    public static String encode(String str, int i){
        StringBuilder res = new StringBuilder();
        Base64.Encoder enc = Base64.getEncoder();
        byte[] encbytes = enc.encode(str.getBytes());
        for (int j = 0; j < encbytes.length; j++)
        {
            res.append((char) encbytes[j]);
        }
        if(i == 30)
            return res.toString();
        return encode(res.toString(), i + 1);
    }
    public static String decode(String str, int i){
        Base64.Decoder dec = Base64.getDecoder();
        byte[] decbytes = dec.decode(str);
        if(i == 30)
            return new String(decbytes);
        return decode(new String(decbytes), i + 1);
    }
}
