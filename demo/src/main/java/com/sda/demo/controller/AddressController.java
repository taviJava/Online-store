package com.sda.demo.controller;

import com.sda.demo.dto.AddressDto;
import com.sda.demo.dto.CategoryDto;
import com.sda.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public void add(@RequestBody AddressDto addressDto) {
        addressService.addAddress(addressDto);
    }
    @DeleteMapping("/address/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        addressService.deleteById(id);
    }
    @GetMapping("/address")
    public List<AddressDto> getAll() {
        return addressService.getAddress();
    }

    @GetMapping("/address/{id}")
    public AddressDto get(@PathVariable(name = "id") Long id) {
        return addressService.get(id);
    }

    @PutMapping("/address")
    public void update(@RequestBody AddressDto addressDto) {
        addressService.update(addressDto);

    }

}
