package model;

public class CartItem {
    public String name;
    public double price;
    public int qty = 1; // default quantity is 1

    // Constructor with name and price (for new items)
    public CartItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Constructor with name, price, and qty (for incrementing items)
    public CartItem(String name, double price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    // Total price for this item
    public double total() {
        return price * qty;
    }
}