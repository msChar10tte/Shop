package ru.netology.shop.filter;

import ru.netology.shop.model.Product;

public interface Filter {
    boolean matches(Product product);
}