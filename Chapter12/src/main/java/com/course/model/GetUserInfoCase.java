package com.course.model;

import lombok.Data;

@Data
public class GetUserInfoCase {
    private int id;
    private String userName;
    private int age;
    private int sex;
    private String expected;
}
