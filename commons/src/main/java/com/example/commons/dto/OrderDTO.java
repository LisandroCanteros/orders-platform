package com.example.commons.dto;

public class OrderDTO {
    private Integer id;
    private String product;
    private int quantity;

    public OrderDTO() {}

    public OrderDTO(Integer id, String product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
