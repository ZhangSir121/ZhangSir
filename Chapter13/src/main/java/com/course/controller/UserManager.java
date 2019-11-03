package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j
@RestController
@Api(value = "v1",description = "用户管理系统")
@RequestMapping("v1")
public class UserManager {
    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登记接口测试",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Boolean login(HttpServletResponse response, @RequestBody User user){
        int i = template.selectOne("login",user);
        if(i == 1){
            Cookie cookie = new Cookie("login","true");
            response.addCookie(cookie);
            log.info("登录用户是："+ user.getUserName());
            return true;
        }
        log.info("未查询到现登录用户");
        return false;
    }

    @ApiOperation(value = "添加用户接口测试",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public Boolean addUser(HttpServletRequest request,@RequestBody User user){
        Boolean b = verifyCookies(request);
        int n = 0;
        if(true == b){
            n = template.insert("addUser",user);
        }
        if (n > 0){
            log.info("添加用户"+user.getUserName()+"成功，数量为:"+n);
            return true;
        }
        log.info("添加用户失败");
        return false;
    }

    @ApiOperation(value = "获取用户信息接口测试",httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean b = verifyCookies(request);
        if(true == b){
            List<User> users = template.selectList("getUserInfo",user);
            log.info("用户信息获取成功，数量为："+users.size());
            return users;
        }
        log.info("获取用户信息失败");
        return null;
    }

    @ApiOperation(value = "更新/删除用户接口测试",httpMethod = "POST")
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public int updateUser(HttpServletRequest request,@RequestBody User user){
        Boolean b = verifyCookies(request);
        int n = 0;
        if(true == b){
            n = template.update("updateUser",user);
        }
        if(n > 0){
            log.info("更新/删除用户成功，数量为："+ n);
            return n;
        }
        log.info("更新/删除用户失败");
       return n;
    }

    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            log.info("获取cookies信息为空");
            return false;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("login") && cookie.getValue().equals("true")){
                log.info("cookies信息验证通过");
               return true;
            }
        }
        return false;
    }

}
