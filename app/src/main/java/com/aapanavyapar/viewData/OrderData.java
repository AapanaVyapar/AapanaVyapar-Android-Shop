package com.aapanavyapar.viewData;

import com.aapanavyapar.aapanavyapar.services.Address;

public class OrderData {
    String orderId;
    String orderProductId;
    String orderStatus;
    String orderDeliveryTimeStamp;
    String orderOrderTimeStamp;
    float orderProductPrice;
    int orderProductQty;
    String orderProductName;
    String orderProductImage;

    Address address;

    public OrderData(String orderProductId, String orderId, String orderStatus, String orderDeliveryTimeStamp, String orderOrderTimeStamp, float orderProductPrice, int orderProductQty, String orderProductName, String orderProductImage, Address address) {
        this.orderProductId = orderProductId;
        this.orderStatus = orderStatus;
        this.orderDeliveryTimeStamp = orderDeliveryTimeStamp;
        this.orderOrderTimeStamp = orderOrderTimeStamp;
        this.orderProductPrice = orderProductPrice;
        this.orderProductQty = orderProductQty;
        this.orderProductName = orderProductName;
        this.orderProductImage = orderProductImage;
        this.address = address;
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDeliveryTimeStamp() {
        return orderDeliveryTimeStamp;
    }

    public void setOrderDeliveryTimeStamp(String orderDeliveryTimeStamp) {
        this.orderDeliveryTimeStamp = orderDeliveryTimeStamp;
    }

    public String getOrderOrderTimeStamp() {
        return orderOrderTimeStamp;
    }

    public void setOrderOrderTimeStamp(String orderOrderTimeStamp) {
        this.orderOrderTimeStamp = orderOrderTimeStamp;
    }

    public float getOrderProductPrice() {
        return orderProductPrice;
    }

    public void setOrderProductPrice(float orderProductPrice) {
        this.orderProductPrice = orderProductPrice;
    }

    public int getOrderProductQty() {
        return orderProductQty;
    }

    public void setOrderProductQty(int orderProductQty) {
        this.orderProductQty = orderProductQty;
    }

    public String getOrderProductName() {
        return orderProductName;
    }

    public void setOrderProductName(String orderProductName) {
        this.orderProductName = orderProductName;
    }

    public String getOrderProductImage() {
        return orderProductImage;
    }

    public void setOrderProductImage(String orderProductImage) {
        this.orderProductImage = orderProductImage;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}