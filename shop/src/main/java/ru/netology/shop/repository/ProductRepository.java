package ru.netology.shop.repository;

import ru.netology.shop.model.Product;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepository {
    Collection<Product> getAll();
    Optional<Product> findById(int id);
}