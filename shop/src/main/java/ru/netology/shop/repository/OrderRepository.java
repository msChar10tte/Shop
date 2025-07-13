package ru.netology.shop.repository;

import ru.netology.shop.model.Order;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(int id);
}