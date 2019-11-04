package com.course.cases;

import com.course.config.ConfigFileName;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetUserListTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户列表接口测试")
    public void getUserList() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(ConfigFileName.getUserListUrl);

        JSONArray resultJson = getJsonResult(getUserListCase);

        List<User> userList = session.selectList(getUserListCase.getExpected(), getUserListCase);
        for(User u:userList){
            System.out.println("数据库查取的user:" + u.toString());
        }
        JSONArray userListJson = new JSONArray(userList);
        Assert.assertEquals(resultJson.length(),userListJson.length());
        for(int i=0; i<resultJson.length();i++){
            JSONObject expect = (JSONObject) userListJson.get(i);
            JSONObject actual = (JSONObject) resultJson.get(i);

        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(ConfigFileName.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("age",getUserListCase.getAge());
        param.put("sex",getUserListCase.getSex());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        ConfigFileName.client.setCookieStore(ConfigFileName.store);
        HttpResponse response = ConfigFileName.client.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;
    }

}
