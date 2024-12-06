package com.itheima.pojo;

public class CartItem {

    private Integer userId;      // 用户 ID
    private Integer brandId;     // 品牌 ID
    private Integer quantity;     // 数量
    private String brandName;// 品牌名称
    private double price;//品牌价格
    private int number;//品牌库存
    private double totalPrice;

    public CartItem(Integer userId, Integer brandId, Integer quantity, String brandName, double price, int number, double totalPrice) {
        this.userId = userId;
        this.brandId = brandId;
        this.quantity = quantity;
        this.brandName = brandName;
        this.price = price;
        this.number = number;
        this.totalPrice = totalPrice;
    }

    public CartItem() {
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    //总价
    public double getTotalPrice() {
        return this.price * this.quantity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "userId=" + userId +
                ", brandId=" + brandId +
                ", quantity=" + quantity +
                ", brandName='" + brandName + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
