package ru.netology.shop.model;

public record Product(int id, String name, double price, String manufacturer) {
    @Override
    public String toString() {
        return String.format("ID: %d | %s (%s) | Цена: %.2f руб.", id, name, manufacturer, price);
    }
}