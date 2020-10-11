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
            List<PrivilegeDto> privilegeDtos = new ArrayList<>();
            for (PrivilegeModel privilegeModel: roleModel.getPrivileges()){
                PrivilegeDto privilegeDto = new PrivilegeDto();
                privilegeDto.setId(privilegeModel.getId());
                privilegeDto.setName(privilegeModel.getName());
                privilegeDtos.add(privilegeDto);
            }
            roleDto.setPrivileges(privilegeDtos);
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    public void add(RoleDto roleDto){
  RoleModel roleModel = new RoleModel();
  roleModel.setName(roleDto.getName());
  List<PrivilegeModel> privilegeModels = new ArrayList<>();
  for (PrivilegeDto privilegeDto: roleDto.getPrivileges()){
      PrivilegeModel privilegeModel = new PrivilegeModel();
      privilegeModel.setId(privilegeDto.getId());
      privilegeModel.setName(privilegeDto.getName());
      privilegeModels.add(privilegeModel);
  }
  roleModel.setPrivileges(privilegeModels);
  roleRepository.save(roleModel);
    }

    public RoleDto getRole(long id){
        RoleModel roleModel = roleRepository.findById(id).orElse(null);
        RoleDto roleDto = new RoleDto();
        roleDto.setId(roleModel.getId());
        roleDto.setName(roleModel.getName());
        List<PrivilegeDto> privilegeDtos = new ArrayList<>();
        for (PrivilegeModel privilegeModel: roleModel.getPrivileges()){
            PrivilegeDto privilegeDto = new PrivilegeDto();
            privilegeDto.setId(privilegeModel.getId());
            privilegeDto.setName(privilegeModel.getName());
            privilegeDtos.add(privilegeDto);
        }
        roleDto.setPrivileges(privilegeDtos);
        return roleDto;
    }

    public void delete(long id){
        roleRepository.deleteById(id);
    }

    public void update(RoleDto roleDto){
        RoleModel roleModel = roleRepository.findById(roleDto.getId()).orElse(null);

        roleModel.setName(roleDto.getName());
        List<PrivilegeModel> privilegeModels = new ArrayList<>();
        for (PrivilegeDto privilegeDto: roleDto.getPrivileges()){
            PrivilegeModel privilegeModel = new PrivilegeModel();
            privilegeModel.setId(privilegeDto.getId());
            privilegeModel.setName(privilegeDto.getName());
            privilegeModels.add(privilegeModel);
        }
        roleModel.setPrivileges(privilegeModels);
        roleRepository.save(roleModel);
    }
}
