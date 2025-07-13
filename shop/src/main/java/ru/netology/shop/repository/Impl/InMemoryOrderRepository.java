package ru.netology.shop.repository.Impl;

import ru.netology.shop.model.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements ru.netology.shop.repository.OrderRepository {
    private final Map<Integer, Order> orders = new HashMap<>();

    @Override
    public void save(Order order) {
        orders.put(order.getId(), order);
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.ofNullable(orders.get(id));
    }
}