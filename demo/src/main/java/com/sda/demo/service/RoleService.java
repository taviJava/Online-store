package com.sda.demo.service;

import com.sda.demo.dto.PrivilegeDto;
import com.sda.demo.dto.RoleDto;
import com.sda.demo.persitance.model.PrivilegeModel;
import com.sda.demo.persitance.model.RoleModel;
import com.sda.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<RoleDto> getAll(){
        List<RoleModel> roleModels = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        for (RoleModel roleModel: roleModels){
            RoleDto roleDto = new RoleDto();
            roleDto.setId(roleModel.getId());
            roleDto.setName(roleModel.getName());
            for (PrivilegeModel privilegeModel: roleModel.getPrivileges()){
                PrivilegeDto privilegeDto = new PrivilegeDto();
                privilegeDto.setId(privilegeModel.getId());
                privilegeDto.setName(privilegeModel.getName());
            }
            roleDto.setPrivileges();
        }
    }
}
