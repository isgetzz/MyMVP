package com.app.ccmvp.sign;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static String getMd5Value(String sSecret) {
    	try {
    	MessageDigest bmd5 = MessageDigest.getInstance("MD5");
    	bmd5.update(sSecret.getBytes());
    	int i;
    	StringBuffer buf = new StringBuffer();
    	byte[] b = bmd5.digest();
    	for (int offset = 0; offset < b.length; offset++) {
    	i = b[offset];
    	if (i < 0)
    	i += 256;
    	if (i < 16)
    	buf.append("0");
    	buf.append(Integer.toHexString(i));
    	}
    	return buf.toString();
    	} catch (NoSuchAlgorithmException e) {
    	e.printStackTrace();
    	}
    	return "";
    	}
    
    public final static String getMD5Str(String str) {  
        MessageDigest messageDigest = null;  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();  
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {  
          Log.e("MD5Util","NoSuchAlgorithmException caught!");
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        byte[] byteArray = messageDigest.digest();  
        StringBuffer md5StrBuff = new StringBuffer();  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
        return md5StrBuff.toString();  
    } 
}
