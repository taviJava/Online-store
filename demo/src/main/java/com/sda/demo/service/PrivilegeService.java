package com.sda.demo.service;

import com.sda.demo.dto.PrivilegeDto;
import com.sda.demo.dto.RoleDto;
import com.sda.demo.persitance.model.PrivilegeModel;
import com.sda.demo.persitance.model.RoleModel;
import com.sda.demo.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public List<PrivilegeDto> getAll(){
        List<PrivilegeModel> privilegeModels = privilegeRepository.findAll();
        List<PrivilegeDto> privilegeDtos = new ArrayList<>();
        for (PrivilegeModel privilegeModel: privilegeModels){
            PrivilegeDto privilegeDto = new PrivilegeDto();
            privilegeDto.setId(privilegeModel.getId());
            privilegeDto.setName(privilegeModel.getName());
            privilegeDtos.add(privilegeDto);
        }
        return privilegeDtos;
    }

    public void add(PrivilegeDto privilegeDto){
        PrivilegeModel privilegeModel = new PrivilegeModel();
        privilegeModel.setName(privilegeDto.getName());
        privilegeRepository.save(privilegeModel);
    }

    public PrivilegeDto getPrivilege(long id){
        PrivilegeModel privilegeModel = privilegeRepository.findById(id).orElse(null);
        PrivilegeDto privilegeDto = new PrivilegeDto();
        privilegeDto.setId(privilegeModel.getId());
        privilegeDto.setName(privilegeModel.getName());

        return privilegeDto;
    }

    public void delete(long id){
        privilegeRepository.deleteById(id);
    }

    public void update(PrivilegeDto privilegeDto){
        PrivilegeModel privilegeModel = privilegeRepository.findById(privilegeDto.getId()).orElse(null);
        privilegeModel.setName(privilegeDto.getName());
        privilegeRepository.save(privilegeModel);
    }
}
