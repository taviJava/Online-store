package com.sda.demo.persitance.model;


import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "user")
public class UserModel {

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

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)

    private AdressModel adress;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
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

    public AdressModel getAdress() {
        return adress;
    }

    public void setAdress(AdressModel adress) {
        this.adress = adress;
    }

    public List<RoleModel> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleModel> roleList) {
        this.roleList = roleList;
    }
}
