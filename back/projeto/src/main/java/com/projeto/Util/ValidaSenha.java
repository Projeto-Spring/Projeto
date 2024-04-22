package com.projeto.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaSenha {
    public static boolean validaSenha(String senha) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(senha);
        return matcher.matches();
    }    
}
