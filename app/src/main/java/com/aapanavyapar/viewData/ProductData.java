package com.aapanavyapar.viewData;

import java.io.Serializable;

public class ProductData implements Serializable {
    String productId;
    String productName;
    String description;
    String shippingInfo;
    int stock;
    int offer;
    double price;
    String[] Images;
    String category;

    public ProductData(String productId, String productName, String description, String shippingInfo, int stock, double price, int offer, String[] images, String category) {
        this.productName = productName;
        this.description = description;
        this.shippingInfo = shippingInfo;
        this.stock = stock;
        this.price = price;
        Images = images;
        this.category = category;
        this.offer = offer;
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(String shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String[] getImages() {
        return Images;
    }

    public String getImagesString() {
        String urls = "";
        int count = 0;
        for (String url : this.Images) {
            if (count > 0){
                urls += ",";
            }
            urls += url;
            count++;
        }
        return urls;
    }

    public void setImages(String[] images) {
        Images = images;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
