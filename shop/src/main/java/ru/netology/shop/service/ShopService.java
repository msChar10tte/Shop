package ru.netology.shop.service;

import ru.netology.shop.cart.Cart;
import ru.netology.shop.filter.Filter;
import ru.netology.shop.model.Order;
import ru.netology.shop.model.Product;
import ru.netology.shop.repository.OrderRepository;
import ru.netology.shop.repository.ProductRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ShopService {
    private static AtomicInteger orderIdCounter = new AtomicInteger(0);

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    public ShopService(ProductRepository productRepository, OrderRepository orderRepository, NotificationService notificationService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    public Collection<Product> getAllProducts() {
        return productRepository.getAll();
    }

    public Optional<Product> findProductById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> filterProducts(Filter filter) {
        return productRepository.getAll().stream()
                .filter(filter::matches)
                .collect(Collectors.toList());
    }

    public Order placeOrder(Cart cart) {
        if (cart.isEmpty()) {
            return null;
        }
        int newOrderId = orderIdCounter.incrementAndGet();
        Order newOrder = new Order(newOrderId, cart.getItems(), cart.calculateTotal());
        orderRepository.save(newOrder);
        notificationService.notifyOrderCreated(newOrder);
        cart.clear();
        return newOrder;
    }

    public Optional<Order> findOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }

    public static void resetOrderIdCounterForTests() {
        orderIdCounter.set(0);
    }
}