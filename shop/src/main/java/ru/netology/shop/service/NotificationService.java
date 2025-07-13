package ru.netology.shop.service;

import ru.netology.shop.model.Order;

public interface NotificationService {
    void notifyOrderCreated(Order order);
}