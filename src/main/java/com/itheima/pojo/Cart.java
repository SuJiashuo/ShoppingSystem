package com.itheima.pojo;

//购物车实体类
public class Cart {
    private Integer id;
    private Integer userId;
    private Integer brandId;
    private Integer quantity;

    public Cart() {
    }

    public Cart(Integer id, Integer userId, Integer brandId, Integer quantity) {
        this.id = id;
        this.userId = userId;
        this.brandId = brandId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", brandId=" + brandId +
                ", quantity=" + quantity +
                '}';
    }
}
