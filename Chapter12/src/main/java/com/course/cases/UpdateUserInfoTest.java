package com.course.cases;

import com.course.config.ConfigFileName;
import com.course.model.UpdateUserInfoCase;
import com.course.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "更改用户信息接口测试")
    public void updateUser() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(ConfigFileName.updateUserInfoUrl);
    }
    @Test(dependsOnGroups = "loginTrue",description = "删除用例信息接口测试")
    public void deleteUser() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(ConfigFileName.updateUserInfoUrl);
    }
}
