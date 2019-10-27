package com.course.utils;

import com.course.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFileUtil {

    private static ResourceBundle bundle = ResourceBundle.getBundle("application",Locale.CHINA);

    public static String getUrl(InterfaceName name){

        String address = bundle.getString("test.url");
        String testUrl = "";

        switch (name){
            case ADDUSERINFO:
                testUrl = address + bundle.getString("addUser.uri");
                break;
            case LOGIN:
                testUrl = address + bundle.getString("login.uri");
                break;
            case GETUSERLIST:
                testUrl = address + bundle.getString("getUserList.uri");
                break;
            case GETUSERINFO:
                testUrl = address + bundle.getString("getUserInfo.uri");
                break;
            case UPDATEUSERINFO:
                testUrl = address + bundle.getString("updateUserInfo.uri");
                break;
            default:
                break;
        }
        return testUrl;
    }

}
