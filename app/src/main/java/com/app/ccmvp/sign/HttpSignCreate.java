package com.app.ccmvp.sign;

import android.util.Base64;
import android.util.Log;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;

public class HttpSignCreate {
    private static String Sign = "tLUoDDkXY6YOlSup";

    public static String GetSignStr(Hashtable<String, String> dict_data, ArrayList<String> ary) {
        try {
            dict_data.put("sign", Sign);
            String jiami = DictToUrlParame(dict_data, ary);
            String encodedString = Base64.encodeToString(jiami.getBytes(), Base64.DEFAULT);
            encodedString = encodedString.trim().replace("\n", "");
            String lowerstr = MD5Util.getMd5Value(encodedString).toLowerCase();
            String signstr = lowerstr.substring(0, 15);
            return signstr;
        } catch (Exception e) {
            Log.e("Exception", "GetSignStr:" + e);
            return null;
        }
    }
    public static boolean IsSignStr(Hashtable<String, String> dict_data, ArrayList<String> ary, String sign) {
        if (sign.equals("")) {
            return false;
        }
        String current_sign = GetSignStr(dict_data, ary);
        if (current_sign == null) {
            return false;
        }
        if (current_sign.equals("")) {
            return false;
        }
        if (current_sign == sign) {
            return true;
        }
        return false;
    }

    public static boolean IsLower(String str) {
        char a1 = str.toCharArray()[0];

        if (a1 >= 'a' && a1 <= 'z') {
            return true;
        } else if (a1 >= 'A' && a1 <= 'Z')
            return false;

        return true;
    }

    public static String DictToUrlParame(Hashtable<String, String> dict_data, ArrayList<String> ary) {
        String url_parame = "";

        int k = 0;
        for (String obj : ary) {
            String objv = dict_data.get(obj);
            if (!obj.equals("sign")) {
                if (k != 0) {
                    url_parame = url_parame + "&";
                }
                url_parame = url_parame + obj + "=" + objv;
                k++;
            }
        }
        if (ary.size() >= 1)
            url_parame = url_parame + "&token=" + dict_data.get("sign");
        else
            url_parame = url_parame + "token=" + dict_data.get("sign");
        return url_parame;
    }

    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
        }
        return "";
    }
}
