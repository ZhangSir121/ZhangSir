package com.course.config;


import org.apache.http.impl.client.DefaultHttpClient;

import java.net.CookieStore;

public class ConfigFileName {

    public static String loginUrl;
    public static String updateUserInfoUrl;
    public static String getUserListUrl;
    public static String getUserInfoUrl;
    public static String addUserUrl;

    public static DefaultHttpClient client;
    public static CookieStore store;

}
