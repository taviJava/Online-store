package com.sda.demo.controller;

import com.sda.demo.dto.OrderDto;
import com.sda.demo.persitance.model.OrderModel;
import com.sda.demo.repository.OrderRepository;
import com.sda.demo.service.OrderService;
import com.sda.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/orders")
    public void add(@RequestBody OrderModel orderModel){
        orderRepository.save(orderModel);
    }



















    @PostMapping("/order/{username}/{id}")
    public void save(@PathVariable(name = "username") String username, @PathVariable(name = "id") Long productID) {
        orderService.addToCart(username, productID);
    }

    @GetMapping("/orders/{id}")
    public OrderDto findById(@PathVariable(name = "id") Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/orders")
    public List<OrderDto> findAll() {
        return orderService.findAll();
    }

    @DeleteMapping("/orders/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        orderService.deleteById(id);
    }

    @PutMapping("/orders")
    public void update(@RequestBody OrderDto orderDTO) {
        orderService.update(orderDTO);
    }

    @GetMapping("/orderslines/{id}")
    public OrderDto getOrderLines(@PathVariable(name = "id") Long id) {
        OrderDto orderDTO = orderService.findById(id);
        return orderDTO;
    }
}
