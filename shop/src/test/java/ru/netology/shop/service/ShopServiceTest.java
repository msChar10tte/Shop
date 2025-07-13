package ru.netology.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.shop.cart.Cart;
import ru.netology.shop.filter.KeywordFilter;
import ru.netology.shop.model.Order;
import ru.netology.shop.model.Product;
import ru.netology.shop.repository.Impl.InMemoryOrderRepository;
import ru.netology.shop.repository.Impl.InMemoryProductRepository;
import ru.netology.shop.repository.OrderRepository;
import ru.netology.shop.repository.ProductRepository;
import ru.netology.shop.service.Impl.ConsoleNotificationService;

import java.util.List;

public class ShopServiceTest {

    private ShopService shopService;
    private Cart cart;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        ShopService.resetOrderIdCounterForTests();

        productRepository = new InMemoryProductRepository();
        orderRepository = new InMemoryOrderRepository();
        NotificationService notificationService = new ConsoleNotificationService();
        shopService = new ShopService(productRepository, orderRepository, notificationService);
        cart = new Cart();
    }

    @Test
    public void shouldFilterProductsByKeyword() {
        KeywordFilter filter = new KeywordFilter("pro");
        List<Product> filteredProducts = shopService.filterProducts(filter);
        Assertions.assertEquals(2, filteredProducts.size());
        Assertions.assertTrue(filteredProducts.stream().anyMatch(p -> p.name().equals("Ноутбук ProBook")));
    }

    @Test
    public void shouldPlaceOrderSuccessfully() {
        Product product = productRepository.findById(2)
                .orElseThrow(() -> new AssertionError("Продукт с ID=2 не найден"));
        cart.addProduct(product);
        Order order = shopService.placeOrder(cart);
        Assertions.assertNotNull(order, "Заказ не должен быть null");
        Assertions.assertEquals(1, order.getId());
        Assertions.assertEquals(60000.0, order.getTotalPrice());
        Assertions.assertTrue(cart.isEmpty(), "Корзина должна быть пустой после оформления заказа");
        Assertions.assertTrue(orderRepository.findById(1).isPresent(), "Заказ должен быть сохранен в репозитории");
    }
}