package com.sda.demo.dto;


import java.util.List;

public class RoleDto {

    private long id;

    private String name;

    private List<PrivilegeDto> privileges;

    private List<UserDto> users;

}
