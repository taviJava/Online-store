package com.sda.demo.service;

import com.sda.demo.persitance.dto.*;
import com.sda.demo.persitance.model.*;
import com.sda.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private PromoCodeService promoCodeService;

    @Autowired
    private PromoCodeRepository promoCodeRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    private OrderModel orderModel(String username){
        List<OrderModel> orderModels = orderRepository.findAll();
        for (OrderModel orderModel: orderModels){
            if (orderModel.getUsername().equals(username) && orderModel.getStatus().name().equals("pending")){
                return orderModel;
            }
        }
        return new OrderModel();
    }

    public ProductDto converProduct(long id){
        ProductModel productModel = productRepository.findById(id).orElse(null);
        ProductDto productDto = new ProductDto();
        if (productModel!=null){
            productDto.setId(productModel.getId());
            productDto.setPrice(productModel.getPrice());
            productDto.setName(productModel.getName());
            // productDto.setPhoto(productModel.getPhoto());
            productDto.setDescription(productModel.getDescription());
            CategoryDto categoryDto =new CategoryDto();
            categoryDto.setId(productModel.getCategory().getId());
            categoryDto.setName(productModel.getCategory().getName());
            productDto.setCategory(categoryDto);
            ManufacturerDto manufacturerDto = new ManufacturerDto();
            manufacturerDto.setId(productModel.getManufacturer().getId());
            manufacturerDto.setName(productModel.getManufacturer().getName());
            productDto.setManufacturer(manufacturerDto);
            return productDto;
        }
        return productDto;
    }

    public void addToCart(String username, Long productID){
        OrderModel order = orderModel(username);

        boolean isAlreadyInBasket = false;
        List<OrderLineModel> orderLineModels = order.getOrderLines();
        for (OrderLineModel orderLineModel: orderLineModels) {
            if(orderLineModel.getProduct().getId() == productID){
                orderLineModel.setProductsQuantity(orderLineModel.getProductsQuantity() + 1);
                orderLineModel.setProductPrice(orderLineModel.getProductsQuantity() * orderLineModel.getProduct().getPrice());
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

    public OrderModel convertOrderDtoinModel(OrderDto orderDto){
        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderDto.getId());
        orderModel.setDeliveryAddress(orderDto.getDeliveryAddress());
        orderModel.setAdditionalComment(orderDto.getAdditionalComment());
return orderModel;
    }

    public void deleteById(Long id){
        orderRepository.deleteById(id);
    }

    public OrderDto findById(Long id) {
        OrderModel order = orderRepository.findById(id).orElse(null);
        OrderDto orderDto = new OrderDto();
        if (order != null) {
            if (order.getPromoCode()!=null){
                PromoCodeDto promoCodeDto = new PromoCodeDto();
                promoCodeDto.setId(order.getPromoCode().getId());
                promoCodeDto.setPromoNumber(order.getPromoCode().getPromoNumber());
                promoCodeDto.setCode(order.getPromoCode().getCode());
                orderDto.setPromoCodeDto(promoCodeDto);
            }
            orderDto.setId(order.getId());
            orderDto.setTotalCost(order.getTotalCost());
            if (order.getAdditionalComment() != null){
                orderDto.setAdditionalComment(order.getAdditionalComment());
            }
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
            orderDTO.setUsername(om.getUsername());
            orderDTO.setStatus(om.getStatus().name());
            if (om.getAdditionalComment() != null){
                orderDTO.setAdditionalComment(om.getAdditionalComment());
            }

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

    public void update(long id, String address, String comment){
        OrderModel orderModel = orderRepository.findById(id).orElse(null);
        assert orderModel != null;
        orderModel.setOrderDate(new Date(System.currentTimeMillis()));
        orderModel.setStatus(StatusModel.completed);
        orderModel.setDeliveryAddress(address);
        orderModel.setAdditionalComment(comment);

            orderRepository.save(orderModel);

    }

    public Double totalPrice(List<OrderLineModel> orderLineModels){
        double total = 0.0;
        for (OrderLineModel olm: orderLineModels) {
            total = total + olm.getProduct().getPrice() * olm.getProductsQuantity();
        }
        return total;
    }

    public void add(OrderDto orderDto){
        if (newOrder(orderDto)) {
            OrderModel orderModel = new OrderModel();
            orderModel.setTotalCost(0.0);
            orderModel.setStatus(StatusModel.valueOf("pending"));
            orderModel.setUsername(orderDto.getUsername());
            UserModel userModel = userRepository.findUserModelByEmail(orderDto.getUsername()).get();
            orderModel.setUserAddress("Country: " + userModel.getAdress().getCountry() + "; City: " + userModel.getAdress().getCity()
                    + "; Street: " + userModel.getAdress().getStreet() + "; Zip Code: " + userModel.getAdress().getZipCode());
            orderRepository.save(orderModel);
        }
    }
    private boolean newOrder(OrderDto orderDto){
        List<OrderDto> orderDtos = findAll();
        List<OrderDto> orderDtosUsername = new ArrayList<>();
        for (OrderDto orderDto1: orderDtos){
            if (orderDto.getUsername().equals(orderDto1.getUsername())){
                orderDtosUsername.add(orderDto1);
            }
        }
        if (orderDtos.size() == 0){
            return true;
        }else if (orderDtos.size() > 0 && orderDtosUsername.size()==0){
            return true;
        }else if (!checkPending(orderDto)){
            return true;
        }

        return false;
    }

    private boolean checkPending(OrderDto orderDto){
        List<OrderDto> orderDtos = findAll();
        List<OrderDto> orderDtosUsername = new ArrayList<>();
        for (OrderDto orderDto1: orderDtos){
            if (orderDto.getUsername().equals(orderDto1.getUsername())){
                orderDtosUsername.add(orderDto1);
            }
        }
        for (OrderDto orderDto1: orderDtosUsername){
            if (orderDto1.getStatus().equals("pending")){
                return true;
            }
        }
        return false;
    }
    private OrderModel getOrder(long id){
        List<OrderModel> orderModels = orderRepository.findAll();
        for (OrderModel orderModel: orderModels){
            for (OrderLineModel orderLineModel: orderModel.getOrderLines()){
                if (orderLineModel.getId() == id){
                    return orderModel;
                }
            }
        }
        return new OrderModel();
    }

    public void updateOrderLineQuantity(int quantity, long id){
        OrderLineModel orderLineModel = orderLineRepository.findById(id).orElse(null);
        OrderModel order = getOrder(id);
        assert orderLineModel != null;
        orderLineModel.setProductsQuantity(quantity);
        orderLineModel.setProductPrice(orderLineModel.getProductsQuantity() * orderLineModel.getProduct().getPrice());
        orderLineRepository.save(orderLineModel);
        order.setTotalCost(totalPrice(order.getOrderLines()));
        orderRepository.save(order);
    }

    public void deleteOrdLn(long id){
        OrderLineModel orderLineModel = orderLineRepository.findById(id).orElse(null);
        OrderModel orderModel = getOrder(id);
        orderModel.getOrderLines().remove(orderLineModel);
        orderModel.setTotalCost(totalPrice(orderModel.getOrderLines()));
        orderRepository.save(orderModel);
    }
    public OrderDto findByUserName(String username) {
        // de aici
        List<OrderModel> orderModels = orderRepository.findAllByUsername(username);
        OrderDto orderDto = new OrderDto();
        for (OrderModel order: orderModels) {
            if (order.getStatus().name().equals("pending")) {
                if (order != null) {
                    if (order.getPromoCode() != null) {
                        PromoCodeDto promoCodeDto = new PromoCodeDto();
                        promoCodeDto.setId(order.getPromoCode().getId());
                        promoCodeDto.setPromoNumber(order.getPromoCode().getPromoNumber());
                        promoCodeDto.setCode(order.getPromoCode().getCode());
                        orderDto.setPromoCodeDto(promoCodeDto);
                    }
                    orderDto.setId(order.getId());
                    orderDto.setTotalCost(order.getTotalCost());
                    if (order.getAdditionalComment() != null) {
                        orderDto.setAdditionalComment(order.getAdditionalComment());
                    }
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

                }
            }
        }
        //pana aici



        return orderDto;
    }

      public void applyPromo(String code,long id){
        PromoCodeDto promoCodeDto = promoCodeService.getPromoCodeByCode(code);
        OrderDto orderDto = findById(id);
        if (orderDto.getPromoCodeDto()==null){  // setez noul pret redus daca nu are nici un cod de reducere
            double totalCost= (orderDto.getTotalCost() - (orderDto.getTotalCost()*(promoCodeDto.getPromoNumber()/100)));
            orderDto.setTotalCost(Math.round(totalCost * 100.0) / 100.0);
        }
          orderDto.setPromoCodeDto(promoCodeDto);
          OrderModel orderModel = orderRepository.findById(orderDto.getId()).orElse(null);

          PromoCode promoCode = promoCodeRepository.getByCode(code).orElse(null);
          if (promoCode!=null){
              assert orderModel != null;
              orderModel.setTotalCost(orderDto.getTotalCost());
              orderModel.setPromoCode(promoCode);
          }
          orderRepository.save(orderModel);
      }

      public void purchase(OrderModel orderModel){
          System.out.println(orderModel.getId());
             // orderModel.setStatus(StatusModel.completed);
              orderModel.setOrderDate(new Date(System.currentTimeMillis()));
            orderRepository.save(orderModel);
          }

      }




