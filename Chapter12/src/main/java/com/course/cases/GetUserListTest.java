package com.course.cases;

import com.course.config.ConfigFileName;
import com.course.model.GetUserListCase;
import com.course.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetUserListTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户列表接口测试")
    public void getUserList() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(ConfigFileName.getUserListUrl);
    }
}
