package com.itheima.pojo;

public class OrderItem {
    private Integer id;
    private Integer userId;
    private String createTime;
    private Integer brandId;
    private Integer quantity;
    private double totalPrice;
    private Integer stat;
    private Integer logistics;


    public OrderItem() {
    }

    public OrderItem(Integer id, Integer userId, String createTime, Integer brandId, Integer quantity, double totalPrice, Integer stat, Integer logistics) {
        this.id = id;
        this.userId = userId;
        this.createTime = createTime;
        this.brandId = brandId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.stat = stat;
        this.logistics = logistics;
    }

    /**
     * 获取
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     * @return userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     * @return brandId
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     * 设置
     * @param brandId
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取
     * @return quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取
     * @return totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * 设置
     * @param totalPrice
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 获取
     * @return stat
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * 设置
     * @param stat
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }

    /**
     * 获取
     * @return logistics
     */
    public Integer getLogistics() {
        return logistics;
    }

    /**
     * 设置
     * @param logistics
     */
    public void setLogistics(Integer logistics) {
        this.logistics = logistics;
    }

    public String toString() {
        return "OrderItem{id = " + id + ", userId = " + userId + ", createTime = " + createTime + ", brandId = " + brandId + ", quantity = " + quantity + ", totalPrice = " + totalPrice + ", stat = " + stat + ", logistics = " + logistics + "}";
    }
}
