package ru.netology.shop.model;

import java.util.List;

public class Order {
    private final int id;
    private final List<Product> products;
    private final double totalPrice;
    private OrderStatus status;

    public Order(int id, List<Product> products, double totalPrice) {
        this.id = id;
        this.products = List.copyOf(products);
        this.totalPrice = totalPrice;
        this.status = OrderStatus.CREATED;
    }

    public int getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Заказ #%d | Статус: %s | Итого: %.2f руб.",
                id, status.getDescription(), totalPrice);
    }
}