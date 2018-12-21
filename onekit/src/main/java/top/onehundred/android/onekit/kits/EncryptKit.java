package top.onehundred.android.onekit.kits;

import android.text.TextUtils;
import android.util.Base64;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密与编码工具类
 */
public class EncryptKit {

    private static EncryptKit encryptKit;
    public static EncryptKit getInstance(){
        if(encryptKit == null){
            synchronized (EncryptKit.class){
                if(encryptKit == null){
                    encryptKit = new EncryptKit();
                }
            }
        }
        return new EncryptKit();
    }

    /**
     * md5 encode
     * @param text
     * @return
     */
    public String md5(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(text.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * SHA-1 encode
     * @param text
     * @return
     */
    public String SHA1(String text) {
        byte[] digesta = null;
        try {
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            alga.update(text.getBytes());
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String rs = byte2hex(digesta);
        return rs;
    }

    private String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    /**
     * base64 encode;
     * @param text
     * @return
     */
    public String base64Encode(String text){
        return Base64.encodeToString(text.getBytes(), Base64.DEFAULT);
    }

    /**
     * base64 decode
     * @param strBase64
     * @return
     */
    public String base64Decode(String strBase64){
        return new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT));
    }

    /**
     * url encode
     * @param text
     * @return
     */
    public String urlEncode(String text){
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
    }

    /**
     * url decode
     * @param text
     * @return
     */
    public String urlDecode(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
    }

    /**
     * 将汉字转换成unicode
     * @param text
     * @return
     */
    public String unicodeEncode(String text) {
        if(TextUtils.isEmpty(text)){
            return "";
        }
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * 将unicode转换成汉字
     * @param unicode
     * @return
     */
    public String unicodeDecode(String unicode) {
        if(TextUtils.isEmpty(unicode)){
            return "";
        }
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }
}
