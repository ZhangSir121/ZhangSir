package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Objects;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HostTest {

    private String url;
    private CookieStore store;
    private ResourceBundle bundle;

    @BeforeTest
    public void getUrl(){
        bundle = ResourceBundle.getBundle("application",Locale.CHINA);
        url = bundle.getString("host.url");
    }
    @Test
    public void loginTest() throws IOException {
        String testUrl = this.url + bundle.getString("host.login.uri");
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(testUrl);

        post.setHeader("content-type","application/x-www-form-urlencoded");

        //装配post请求参数
        Map<String,Object> param = new HashMap<String, Object>();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        param.put("username","admin");
        param.put("password","admin");
        for (String key : param.keySet()) {
            list.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
        }

        //将参数进行编码为合适的格式,如将键值对编码为param1=value1&param2=value2
        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(list,"utf-8");
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity(),"utf-8");

        System.out.println(result);

        JSONObject object = new JSONObject(result);
        System.out.println(object.get("status"));

       // JSONObject object1 = new JSONObject(object.get("data"));
        JSONObject jsonObject= (JSONObject) object.get("data");
        System.out.println(jsonObject);
        String name= (String) jsonObject.get("username");
        System.out.println(name);

        Assert.assertEquals(name,"admin");

        this.store = client.getCookieStore();

    }
    @Test(dependsOnMethods = "loginTest")
    public void getUser() throws IOException {

        String testUrl  =this.url + bundle.getString("host.getUser.uri");

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(testUrl);

        post.setHeader("content-type","application/x-www-form-urlencoded");
        client.setCookieStore(this.store);

        HttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println("getUser:"+result);

    }


}
