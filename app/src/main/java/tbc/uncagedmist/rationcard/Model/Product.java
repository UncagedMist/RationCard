package tbc.uncagedmist.rationcard.Model;

public class Product {
    private String productId,stateId,productName,productImage,productUrl;

    public Product() {
    }

    public Product(String productId, String stateId, String productName, String productImage, String productUrl) {
        this.productId = productId;
        this.stateId = stateId;
        this.productName = productName;
        this.productImage = productImage;
        this.productUrl = productUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
