package entity;

public class Purchase {

    private String item;
    private Double price;

    public Purchase(String item, Double price) {
        this.item = item;
        this.price = price;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
