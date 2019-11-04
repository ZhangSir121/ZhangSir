package com.course.cases;

import com.course.config.ConfigFileName;
import com.course.model.AdduserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AdduserCase adduserCase = session.selectOne("adduserCase",1);
        System.out.println(adduserCase.toString());
        System.out.println(ConfigFileName.addUserUrl);

        String result = getResult(adduserCase);

        Thread.sleep(3000);

        List<User> userList = session.selectList("addUser",adduserCase);
        for (User u:userList){
            System.out.println(u.toString());
        }
        Assert.assertEquals(result,adduserCase.getExpected());
    }

    private String getResult(AdduserCase adduserCase) throws IOException {
        HttpPost post = new HttpPost(ConfigFileName.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName",adduserCase.getUserName());
        param.put("password",adduserCase.getPassword());
        param.put("sex",adduserCase.getSex());
        param.put("age",adduserCase.getAge());
        param.put("permission",adduserCase.getPermission());
        param.put("isDelete",adduserCase.getIsDelete());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        ConfigFileName.client.setCookieStore(ConfigFileName.store);
        System.out.println(ConfigFileName.store);
        HttpResponse response = ConfigFileName.client.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        return result;
    }
}
