package com.krokochik.CampfireGallery.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticationManager {
    private ArrayList<String> keys = new ArrayList<>();
    private static Byte KEY_LENGTH = 10;
    private static final String CHARS_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHARS_UPPERCASE = CHARS_LOWERCASE.toUpperCase();
    private static final String NUMBERS = "1234567890";
    private static final String SPECIAL_SYMBOLS = "~$^<>";

    public boolean isExist(String key){
        for (String element : keys) {
            if (element.equals(key))
                return true;
        }
        return false;
    }
    public boolean addKey(String key) throws KeySizeException {
        if (key.length() == KEY_LENGTH) {
            if (!isExist(key)) {
                return keys.add(key);
            } else return false;
        } else throw new KeySizeException();
    }
    public short getLastKeyId(){
        return (short) (keys.size() - 1);
    }
    public boolean removeKey(String key){
        if (isExist(key)) {
            return keys.remove(key);
        }
        else return false;
    }
    public boolean addGeneratedKey(){
        try {
            return addKey(generateKey());
        } catch (KeySizeException ignored) {}
        return false;
    }
    public static String generateKey(int keyLength){
        StringBuilder res = new StringBuilder();
        int j = 0;
        for (int i = 0; i < keyLength; i++) {
            if(++j == 4)
                j = 0;
            switch (j) {
                case 0 -> res.append(CHARS_LOWERCASE.charAt((int) (Math.random() * CHARS_LOWERCASE.length())));
                case 1 -> res.append(CHARS_UPPERCASE.charAt((int) (Math.random() * CHARS_UPPERCASE.length())));
                case 2 -> res.append(NUMBERS.charAt((int) (Math.random() * NUMBERS.length())));
                case 3 -> res.append(SPECIAL_SYMBOLS.charAt((int) (Math.random() * SPECIAL_SYMBOLS.length())));
            }
        }
        return shuffle(res.toString());
    }
    public static String generateKey(){
        return generateKey(KEY_LENGTH);
    }
    public void removeKey(short id){
        keys.remove(id);
    }
    public void removeAllUserKeys(){
        String devKey = keys.get(0);
        keys.clear();
        keys.add(devKey);
    }
    public String[] getAllKeys(){
        String[] res = new String[keys.size()];
        for (int i = 0; i < keys.size(); i++){
            res[i] = keys.get(i);
        }
        return res;
    }

    public static String shuffle(String input){
        List<Character> characters = new ArrayList<Character>();
        for (char c:input.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while (characters.size()!=0) {
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }
    public byte getKeyLength(){
        return KEY_LENGTH;
    }
    public void setKeyLength(byte length){
        KEY_LENGTH = length;
    }

    public AuthenticationManager(String key) throws KeySizeException{
        KEY_LENGTH = (byte)key.length();
        addKey(key);
    }
    public AuthenticationManager(byte length){
        KEY_LENGTH = length;
    }
    public AuthenticationManager(int length){
        KEY_LENGTH = (byte)length;
    }
    public AuthenticationManager(){}

    public static class KeySizeException extends Exception{
        public KeySizeException(){
            super();
        }
    }
}
