package ru.netology.shop.cart;

import ru.netology.shop.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private final List<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        items.add(product);
    }



    public List<Product> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(Product::price).sum();
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}