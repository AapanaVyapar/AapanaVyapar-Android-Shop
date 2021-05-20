package com.aapanavyapar.viewData;

public class ProductData {
    String productName;
    String description;
    String shippingInfo;
    int stock;
    double price;
    String[] Images;
    String category;

    public ProductData(String productName, String description, String shippingInfo, int stock, double price, String[] images, String category) {
        this.productName = productName;
        this.description = description;
        this.shippingInfo = shippingInfo;
        this.stock = stock;
        this.price = price;
        Images = images;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
