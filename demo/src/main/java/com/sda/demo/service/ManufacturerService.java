package com.sda.demo.service;

import com.sda.demo.dto.AddressDto;
import com.sda.demo.dto.ManufacturerDto;
import com.sda.demo.persitance.model.AdressModel;
import com.sda.demo.persitance.model.ManufacturerModel;
import com.sda.demo.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<ManufacturerDto> getManufacturer() {
        List<ManufacturerModel> manufacturerModelList = manufacturerRepository.findAll();
        List<ManufacturerDto> manufacturerDtoList = new ArrayList<>();
        for (ManufacturerModel manufacturerModel : manufacturerModelList) {
            ManufacturerDto manufacturerDto = new ManufacturerDto();
            manufacturerDto.setId(manufacturerModel.getId());
            manufacturerDto.setName(manufacturerDto.getName());
            manufacturerDtoList.add(manufacturerDto);
        }
        return manufacturerDtoList;
    }

    public void addManufacturer(ManufacturerDto manufacturerDto) {
        ManufacturerModel manufacturerModel = new ManufacturerModel();
        manufacturerModel.setId(manufacturerDto.getId());
        manufacturerModel.setName(manufacturerDto.getName());
        manufacturerRepository.save(manufacturerModel);
    }

    public void deleteById(Long Id) {
        manufacturerRepository.deleteById(Id);

    }

    public void update(ManufacturerDto manufacturerDto) {
        Optional<ManufacturerModel> manufacturerModelOptional = manufacturerRepository.findById(manufacturerDto.getId());
        if (manufacturerModelOptional.isPresent()) {
            ManufacturerModel manufacturerModel = manufacturerModelOptional.get();
            manufacturerModel.setId(manufacturerDto.getId());
            manufacturerModel.setName(manufacturerDto.getName());
            manufacturerRepository.save(manufacturerModel);
        }
    }
}