package com.sda.demo.service;


import com.sda.demo.persitance.dto.AddressDto;
import com.sda.demo.persitance.dto.PrivilegeDto;
import com.sda.demo.persitance.dto.RoleDto;
import com.sda.demo.persitance.dto.UserDto;
import com.sda.demo.persitance.model.*;
import com.sda.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(UserDto userDto) {
        UserModel userModel = new UserModel();
        AddressDto addressDto = userDto.getAdress();
        AdressModel addressModel = new AdressModel();

        if(addressDto != null){
            addressModel.setCountry(addressDto.getCountry());
            addressModel.setZipCode(addressDto.getZipCode());
            addressModel.setStreet(addressDto.getStreet());
            addressModel.setCity(addressDto.getCity());

        }
        userModel.setAdress(addressModel);
        userModel.setId(userDto.getId());
        userModel.setEmail(userDto.getEmail());
        userModel.setPassword(userDto.getPassword());
        List<UserModel> users = userRepository.findAll();
        if (users.size()== 0){
            userModel.setRole(Role.valueOf("Administrator"));
        }else{
            userModel.setRole(Role.valueOf("Standard"));
        }
        userRepository.save(userModel);
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    public List<UserDto> findALl(){
        List<UserModel> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();

        for(UserModel userModel : users){
            UserDto userDto = new UserDto();

            userDto.setId(userModel.getId());
            userDto.setEmail(userModel.getEmail());
            userDto.setPassword(userModel.getPassword());
            userDto.setRole(userModel.getRole().name());

            AdressModel addressModel = userModel.getAdress();
            AddressDto addressDto = new AddressDto();

            addressDto.setZipCode(addressModel.getZipCode());
            addressDto.setCountry(addressModel.getCountry());
            addressDto.setCity(addressModel.getCity());
            addressDto.setStreet(addressModel.getStreet());
            addressDto.setId(addressModel.getId());

            userDto.setAdress(addressDto);
            List<RoleDto> roleDtos = new ArrayList<>();
            for (RoleModel roleModel: userModel.getRoleList()){
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
            userDto.setRoleList(roleDtos);
            usersDto.add(userDto);

        }
        return usersDto;
    }
    public void update(UserDto userDto){
        UserModel userModel = convertUser(userDto);
            userRepository.save(userModel);
        }

  public UserModel convertUser(UserDto userDto){
      Optional<UserModel> newUser = userRepository.findById(userDto.getId());
      UserModel userModel = newUser.orElse(null);
      assert userModel != null;
      userModel.setRole(Role.valueOf(userDto.getRole()));
      //userModel.setEmail(userDto.getEmail());
      AdressModel adressModel = new AdressModel();
      AddressDto addressDto = userDto.getAdress();
      adressModel.setCity(addressDto.getCity());
      adressModel.setCountry(addressDto.getCountry());
      adressModel.setId(addressDto.getId());
      adressModel.setStreet(addressDto.getStreet());
      userModel.setAdress(adressModel);
     return userModel;
    }
    public UserDto findById(Long id ){
        Optional<UserModel>userModel = userRepository.findById(id);
        UserDto userDto = new UserDto();
        if(userModel.isPresent()){
            userDto.setId(userModel.get().getId());
            userDto.setEmail(userModel.get().getEmail());
            userDto.setPassword(userModel.get().getPassword());
            userDto.setRole(userModel.get().getRole().name());

            AdressModel addressModel = userModel.get().getAdress();
            AddressDto addressDto = new AddressDto();

            addressDto.setZipCode(addressModel.getZipCode());
            addressDto.setCountry(addressModel.getCountry());
            addressDto.setCity(addressModel.getCity());
            addressDto.setStreet(addressModel.getStreet());
            addressDto.setId(addressModel.getId());

            userDto.setAdress(addressDto);
            List<RoleDto> roleDtos = new ArrayList<>();
            //de aici o sa sterg
            for (RoleModel roleModel: userModel.get().getRoleList()){
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
            userDto.setRoleList(roleDtos);
            // pana aici
        }

        return userDto;

    }

    public UserDto findByUsername(String username){
        UserModel userModel=userRepository.getUserModelByEmail(username).orElse(new UserModel());
        UserDto userDto = new UserDto();

            userDto.setId(userModel.getId());
            userDto.setEmail(userModel.getEmail());
            userDto.setPassword(userModel.getPassword());
            userDto.setRole(userModel.getRole().name());

            AdressModel addressModel = userModel.getAdress();
            AddressDto addressDto = new AddressDto();

            addressDto.setZipCode(addressModel.getZipCode());
            addressDto.setCountry(addressModel.getCountry());
            addressDto.setCity(addressModel.getCity());
            addressDto.setStreet(addressModel.getStreet());
            addressDto.setId(addressModel.getId());

            userDto.setAdress(addressDto);
            List<RoleDto> roleDtos = new ArrayList<>();
            for (RoleModel roleModel: userModel.getRoleList()){
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
            userDto.setRoleList(roleDtos);


        return userDto;
    }




}
