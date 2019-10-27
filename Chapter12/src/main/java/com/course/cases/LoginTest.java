package com.course.cases;

import com.course.config.ConfigFileName;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFileUtil;
import com.course.utils.DatabaseUtil;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
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
    }
    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(ConfigFileName.loginUrl);
    }


}
