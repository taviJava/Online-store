package com.sda.demo.persitance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String photo;
    private String description;
    private double price;
    @OneToOne(cascade = CascadeType.ALL)
    private OrderLineModel orderline;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("products")
    private CategoryModel subcategory;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("products")
    private ManufacturerModel manufacturer;

    public OrderLineModel getOrderline() {
        return orderline;
    }

    public void setOrderline(OrderLineModel orderline) {
        this.orderline = orderline;
    }

    public CategoryModel getCategory() {
        return subcategory;
    }

    public void setCategory(CategoryModel subcategory) {
        this.subcategory = subcategory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ManufacturerModel getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerModel manufacturer) {
        this.manufacturer = manufacturer;
    }
}
