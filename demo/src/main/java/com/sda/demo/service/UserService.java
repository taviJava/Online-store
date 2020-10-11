package com.sda.demo.service;

import com.sda.demo.dto.AddressDto;
import com.sda.demo.dto.UserDto;
import com.sda.demo.persitance.model.AdressModel;
import com.sda.demo.persitance.model.AdressModel;
import com.sda.demo.persitance.model.UserModel;
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
            addressModel.setId(addressDto.getId());
            addressModel.setCountry(addressDto.getCountry());
            addressModel.setZipCode(addressDto.getZipCode());
            addressDto.setStreet(addressDto.getStreet());
            addressDto.setCity(addressDto.getCity());

        }
        userModel.setAdress(addressModel);
        userModel.setId(userDto.getId());
        userModel.setEmail(userDto.getEmail());

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
            AddressDto addressDto = new AddressDto();

            userDto.setId(userModel.getId());
            userDto.setUrl(userModel.getUrl());
            userDto.setEmail(userModel.getEmail());

            AdressModel addressModel = userModel.getAdress();
            if(addressModel != null){
                addressDto.setId(addressModel.getId());
                addressDto.setStreet(addressModel.getStreet());
                addressDto.setCity(addressModel.getStreet());
                addressDto.setCountry(addressModel.getCountry());
                addressDto.setZipCode(addressModel.getZipCode());

            }
            userDto.setAdress(addressDto);
            usersDto.add(userDto);

        }
        return usersDto;
    }
    public void update(UserDto userDto){
        Optional<UserModel> newUser = userRepository.findById(userDto.getId());
        if(newUser.isPresent()){
            UserModel userModel = newUser.get();
            userModel.setEmail(userDto.getEmail());
            userDto.setUrl(userDto.getUrl());
            userRepository.save(userModel);
        }
    }
    public UserDto findById(Long id ){
        Optional<UserModel>userModel = userRepository.findById(id);
        UserDto userDto = new UserDto();
        if(userModel.isPresent()){
            userDto.setId(userModel.get().getId());
            userDto.setUrl(userModel.get().getUrl());
            userDto.setEmail(userModel.get().getEmail());

            AdressModel addressModel = userModel.get().getAdress();
            AddressDto addressDto = new AddressDto();

            addressDto.setZipCode(addressModel.getZipCode());
            addressDto.setCountry(addressModel.getCountry());
            addressDto.setCity(addressModel.getCity());
            addressDto.setStreet(addressModel.getStreet());
            addressDto.setId(addressModel.getId());

            userDto.setAdress(addressDto);
        }
        return userDto;

    }


}
