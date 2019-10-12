package com.ccsu.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-11 21:41
 */
public class MD5Util {
    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }
    private static final String salt = "1a2b3c4d";
    public static String inputPass2formPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPass2dbPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPass2formPass(inputPass);
        String dbPass = formPass2dbPass(formPass, saltDB);
        return dbPass;
    }
}
