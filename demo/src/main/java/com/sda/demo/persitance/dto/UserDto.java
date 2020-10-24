package com.sda.demo.persitance.dto;

import java.util.List;

public class UserDto {

    private long id;
    private String password;
    private String newPassword;
    private String email;
    private AddressDto adress;
    private List<RoleDto> roleList;
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
