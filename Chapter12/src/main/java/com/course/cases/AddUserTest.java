package com.course.cases;

import com.course.config.ConfigFileName;
import com.course.model.AdduserCase;
import com.course.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AdduserCase adduserCase = session.selectOne("adduserCase",1);
        System.out.println(adduserCase.toString());
        System.out.println(ConfigFileName.addUserUrl);
    }
}
