package com.sda.demo.persitance.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sda.demo.persitance.model.AdressModel;
import com.sda.demo.persitance.model.RoleModel;

import javax.persistence.*;
import java.util.List;

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Transient
    @JsonProperty
    private String newPassword;

    private String email;

    private String url;

    @OneToOne(cascade = CascadeType.ALL)

    private AdressModel adressModel;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userList")

    private List<RoleModel> roleList;


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

    public AdressModel getAdressModel() {
        return adressModel;
    }

    public void setAdressModel(AdressModel adressModel) {
        this.adressModel = adressModel;
    }

    public List<RoleModel> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleModel> roleList) {
        this.roleList = roleList;
    }
}
