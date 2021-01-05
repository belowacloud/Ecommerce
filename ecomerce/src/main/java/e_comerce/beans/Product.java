package e_comerce.beans;

public class Product {
    private String productId;
    private String productName;
    private String productUnit;
    private int Quantity;
    private float Price;

    public Product() {
    }

    public Product(String productId, String productName, String productUnit, int quantity, float price) {
        this.productId = productId;
        this.productName = productName;
        this.productUnit = productUnit;
        Quantity = quantity;
        Price = price;
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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productUnit='" + productUnit + '\'' +
                ", Quantity=" + Quantity +
                ", Price=" + Price +
                '}';
    }
}
