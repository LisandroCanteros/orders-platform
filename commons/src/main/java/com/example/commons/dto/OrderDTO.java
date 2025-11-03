package com.example.commons.dto;

public class OrderDTO {
    private Long id;
    private String product;
    private int quantity;

    public OrderDTO() {}

    public OrderDTO(Long id, String product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
