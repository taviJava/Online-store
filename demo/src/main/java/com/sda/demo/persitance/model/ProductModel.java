package com.sda.demo.persitance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private double price;
    @OneToOne(cascade = CascadeType.ALL)
    private OrderLineModel orderline;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("products")
    private CategoryModel category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("products")
    private ManufacturerModel manufacturer;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnoreProperties("product")
    private PhotoPModel photos;

    public OrderLineModel getOrderline() {
        return orderline;
    }

    public void setOrderline(OrderLineModel orderline) {
        this.orderline = orderline;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public PhotoPModel getPhotos() {
        return photos;
    }

    public void setPhotos(PhotoPModel photos) {
        this.photos = photos;
    }

    public void setManufacturer(ManufacturerModel manufacturer) {
        this.manufacturer = manufacturer;
    }

}
