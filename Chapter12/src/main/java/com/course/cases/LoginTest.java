package com.course.cases;

import com.course.config.ConfigFileName;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFileUtil;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试前准备，获取httpClient对象等")
    public void beforeTest(){
        ConfigFileName.addUserUrl = ConfigFileUtil.getUrl(InterfaceName.ADDUSERINFO);
        ConfigFileName.getUserInfoUrl = ConfigFileUtil.getUrl(InterfaceName.GETUSERINFO);
        ConfigFileName.getUserListUrl = ConfigFileUtil.getUrl(InterfaceName.GETUSERLIST);
        ConfigFileName.updateUserInfoUrl = ConfigFileUtil.getUrl(InterfaceName.UPDATEUSERINFO);
        ConfigFileName.loginUrl = ConfigFileUtil.getUrl(InterfaceName.LOGIN);

        ConfigFileName.client = new DefaultHttpClient();
    }
    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(ConfigFileName.loginUrl);

        String result = getResult(loginCase);

        Assert.assertEquals(result,loginCase.getExpected());

    }

    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(ConfigFileName.loginUrl);

        String result = getResult(loginCase);

        Assert.assertEquals(result,loginCase.getExpected());

    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(ConfigFileName.loginUrl);
        JSONObject param = new JSONObject();
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        HttpResponse response = ConfigFileName.client.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        ConfigFileName.client.getCookieStore();
        return result;
    }

}
