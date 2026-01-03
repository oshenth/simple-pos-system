package model;

public class CartItem {
    public String name;
    public double price;
    public int qty = 1;

    public CartItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double total() {
        return price * qty;
    }
}