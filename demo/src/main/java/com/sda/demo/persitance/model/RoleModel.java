package com.sda.demo.persitance.model;

import javax.persistence.*;
import java.util.List;

public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id",referencedColumnName = "id"))
        private List<PrivilegeModel>privilegeList;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="roles_users",
            joinColumns = @JoinColumn(
                    name = "role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",referencedColumnName = "id"))
    private List<UserModel> userList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PrivilegeModel> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<PrivilegeModel> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public List<UserModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
    }
}
