package com.sda.demo.dto;

import java.util.List;

public class UserDto {

    private long id;
    private String password;
    private String newPassword;
    private String email;
    private String url;
    private AddressDto adress;
    private List<RoleDto> roleList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<RoleDto> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDto> roleList) {
        this.roleList = roleList;
    }

    public AddressDto getAdress() {
        return adress;
    }

    public void setAdress(AddressDto adress) {
        this.adress = adress;
    }
}
