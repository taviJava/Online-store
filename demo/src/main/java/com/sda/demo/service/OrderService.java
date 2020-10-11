package com.sda.demo.service;

import com.sda.demo.dto.OrderDto;
import com.sda.demo.dto.OrderLineDto;
import com.sda.demo.dto.ProductDto;
import com.sda.demo.persitance.model.OrderLineModel;
import com.sda.demo.persitance.model.OrderModel;
import com.sda.demo.persitance.model.ProductModel;
import com.sda.demo.repository.OrderRepository;
import com.sda.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addToCart(String username, Long productID){
        Optional<OrderModel> orderModelOptional = orderRepository.findOrderModelByUserName(username);
        OrderModel order;

        boolean isAlreadyInBasket = false;
        if(orderModelOptional.isPresent()) {
            order  = orderModelOptional.get();
            List<OrderLineModel> orderLineModels = order.getOrderLines();
            for (OrderLineModel orderLineModel: orderLineModels) {
                if(orderLineModel.getProduct().getId() == productID){
                    orderLineModel.setProductsQuantity(orderLineModel.getProductsQuantity() + 1);
                    isAlreadyInBasket = true;
                }
            }
            if(!isAlreadyInBasket){
                OrderLineModel orderLineModel = new OrderLineModel();
                orderLineModel.setProductsQuantity(1);
                orderLineModel.setProduct(productRepository.findById(productID).orElse(null));
                orderLineModel.setProductPrice(orderLineModel.getProductsQuantity() * orderLineModel.getProduct().getPrice());
                order.getOrderLines().add(orderLineModel);
                order.setTotalCost(totalPrice(order.getOrderLines()));
                orderRepository.save(order);
            }
            order.setTotalCost(totalPrice(order.getOrderLines()));
            orderRepository.save(order);
        }
        else{
            order = new OrderModel();
            OrderLineModel orderLineModel = new OrderLineModel();
            orderLineModel.setProductsQuantity(1);
            orderLineModel.setProduct(productRepository.findById(productID).orElse(null));
            orderLineModel.setProductPrice(orderLineModel.getProductsQuantity() * orderLineModel.getProduct().getPrice());
            order.getOrderLines().add(orderLineModel);
            order.setTotalCost(totalPrice(order.getOrderLines()));
            orderRepository.save(order);

        }
    }

    public void deleteById(Long id){
        orderRepository.deleteById(id);
    }

    public OrderDto findById(Long id) {
        OrderModel order = orderRepository.findById(id).orElse(null);
        OrderDto orderDto = new OrderDto();
        if (order != null) {
            orderDto.setId(order.getId());
            orderDto.setTotalCost(order.getTotalCost());

            List<OrderLineDto> orderLineDtos = new ArrayList<>();
            for (OrderLineModel ol : order.getOrderLines()) {
                OrderLineDto old = new OrderLineDto();
                old.setId(ol.getId());
                old.setProductPrice(ol.getProductPrice());
                old.setProductsQuantity(ol.getProductsQuantity());

                ProductDto productDto = new ProductDto();
                productDto.setId(ol.getProduct().getId());
                productDto.setName(ol.getProduct().getName());
                productDto.setPrice(ol.getProduct().getPrice());
                old.setProduct(productDto);
                orderLineDtos.add(old);
            }
            orderDto.setOrderLines(orderLineDtos);
        }
        return orderDto;

    }


    public List<OrderDto> findAll(){
        List<OrderDto> orderDTOS = new ArrayList<>();
        List<OrderModel> orderModels = orderRepository.findAll();
        for (OrderModel om: orderModels) {
            OrderDto orderDTO = new OrderDto();
            orderDTO.setId(om.getId());
            orderDTO.setTotalCost(om.getTotalCost());

            List<OrderLineDto> orderLinesDTO = new ArrayList<>();

            for (OrderLineModel orderLineModel : om.getOrderLines()) {
                OrderLineDto old = new OrderLineDto();
                old.setId(orderLineModel.getId());
                old.setProductPrice(orderLineModel.getProductPrice());
                old.setProductsQuantity(orderLineModel.getProductsQuantity());

                ProductDto productDto = new ProductDto();
                productDto.setId(orderLineModel.getProduct().getId());
                productDto.setName(orderLineModel.getProduct().getName());
                productDto.setPrice(orderLineModel.getProduct().getPrice());
                old.setProduct(productDto);
                orderLinesDTO.add(old);
            }
            orderDTO.setOrderLines(orderLinesDTO);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    public void update(OrderDto orderDto){
        OrderModel orderModel = orderRepository.findById(orderDto.getId()).orElse(null);
            orderModel.setTotalCost(orderDto.getTotalCost());
            List<OrderLineModel> orderlines = new ArrayList<>();
            for (OrderLineDto orderLineDTO: orderDto.getOrderLines()) {
                OrderLineModel orderLineModel = new OrderLineModel();
                orderLineModel.setProductsQuantity(orderLineDTO.getProductsQuantity());

                ProductModel productModel = new ProductModel();
                productModel.setName(orderLineDTO.getProduct().getName());
                productModel.setPrice(orderLineDTO.getProduct().getPrice());
                orderLineModel.setProduct(productModel);
                orderlines.add(orderLineModel);
            }
            orderModel.setOrderLines(orderlines);
            orderRepository.save(orderModel);

    }

    public Double totalPrice(List<OrderLineModel> orderLineModels){
        Double total = 0.0;
        for (OrderLineModel olm: orderLineModels) {
            total = total + olm.getProduct().getPrice() * olm.getProductsQuantity();
        }
        return total;
    }

}
