package com.sda.demo.controller;

import com.sda.demo.persitance.model.PrivilegeModel;
import com.sda.demo.persitance.model.RoleModel;
import com.sda.demo.persitance.model.UserModel;
import com.sda.demo.repository.PrivilegeRepository;
import com.sda.demo.repository.RoleRepository;
import com.sda.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class SecurityController {

    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/role/assignprivileges/{id}")
    public void assignPrivilegesToRole(@PathVariable(name = "id") Long roleId, @RequestBody List<PrivilegeModel> privilegeList) {
        RoleModel role = roleRepository.findById(roleId).orElse(null);
        role.getPrivileges().addAll(privilegeList);
        roleRepository.save(role);
    }

    @PostMapping("/role/unassignprivileges/{id}")
    public void unassignPrivilegesFromRole(@PathVariable(name = "id") Long roleId, @RequestBody List<PrivilegeModel> privilegeList) {
        RoleModel role = roleRepository.findById(roleId).orElse(null);
        role.getPrivileges().removeIf(a -> privilegeList.stream().filter(b -> b.getId() == a.getId()).collect(Collectors.toList()).size() > 0);
        roleRepository.save(role);
    }


    //Assign Users
    @PostMapping("/role/assignusers/{id}")
    public void assignUsersToRole(@PathVariable(name = "id") Long roleId, @RequestBody List<UserModel> userList) {
        RoleModel role = roleRepository.findById(roleId).orElse(null);
        role.getUsers().addAll(userList);
        roleRepository.save(role);
    }

    @PostMapping("/role/unassignusers/{id}")
    public void unassignUsersFromRole(@PathVariable(name = "id") Long roleId, @RequestBody List<UserModel> userList) {
        RoleModel role = roleRepository.findById(roleId).orElse(null);
        role.getUsers().removeIf(a -> userList.stream().filter(b -> b.getId() == a.getId()).collect(Collectors.toList()).size() > 0);
        roleRepository.save(role);
    }


}
