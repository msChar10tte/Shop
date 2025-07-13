package ru.netology.shop.repository.Impl;

import ru.netology.shop.model.Product;
import ru.netology.shop.repository.ProductRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<Integer, Product> products = new HashMap<>();

    public InMemoryProductRepository() {
        products.put(1, new Product(1, "Ноутбук ProBook", 85000.0, "HP"));
        products.put(2, new Product(2, "Смартфон Galaxy", 60000.0, "Samsung"));
        products.put(3, new Product(3, "Наушники WH-1000", 25000.0, "Sony"));
        products.put(4, new Product(4, "Монитор UltraWide", 45000.0, "LG"));
        products.put(5, new Product(5, "Клавиатура Pro", 12000.0, "Logitech"));
    }

    @Override
    public Collection<Product> getAll() {
        return Collections.unmodifiableCollection(products.values());
    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.ofNullable(products.get(id));
    }
}