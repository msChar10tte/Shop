package ru.netology.shop.service.Impl;

import ru.netology.shop.model.Order;
import ru.netology.shop.service.NotificationService;

public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notifyOrderCreated(Order order) {
        System.out.println("------------------------------------");
        System.out.printf("Уведомление: Успешно создан заказ #%d на сумму %.2f руб.\n",
                order.getId(), order.getTotalPrice());
        System.out.println("------------------------------------");
    }
}