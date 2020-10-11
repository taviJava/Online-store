package com.sda.demo.service;

import com.sda.demo.dto.AddressDto;
import com.sda.demo.persitance.model.AdressModel;
import com.sda.demo.persitance.model.UserModel;
import com.sda.demo.repository.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AdressRepository adressRepository;


    public List<AddressDto> getAddress() {
        List<AdressModel> adressModelList = adressRepository.findAll();
        List<AddressDto> addressDtoList = new ArrayList<>();
        for (AdressModel adressModel : adressModelList) {
            AddressDto addressDto = new AddressDto();
            addressDto.setCountry(adressModel.getCountry());
            addressDto.setCity(adressModel.getCity());
            addressDto.setStreet(adressModel.getStreet());
            addressDto.setZipCode(adressModel.getZipCode());
            addressDtoList.add(addressDto);
        }
        return addressDtoList;
    }

    public void addAddress(AddressDto addressDto) {
        AdressModel adressModel = new AdressModel();
        adressModel.setCountry(addressDto.getCountry());
        adressModel.setCity(addressDto.getCity());
        adressModel.setStreet(addressDto.getStreet());
        adressModel.setZipCode(addressDto.getZipCode());
        adressRepository.save(adressModel);
    }

    public void deleteById(Long Id) {
        adressRepository.deleteById(Id);

    }

    public void update(AddressDto addressDto) {
        Optional<AdressModel> adressModelOptional = adressRepository.findById(addressDto.getId());
        if (adressModelOptional.isPresent()) {
            AdressModel adressModel = adressModelOptional.get();
            adressModel.setCountry(addressDto.getCountry());
            adressModel.setCity(addressDto.getCity());
            adressModel.setStreet(addressDto.getStreet());
            adressModel.setZipCode(addressDto.getZipCode());
            adressRepository.save(adressModel);
        }
    }

}
