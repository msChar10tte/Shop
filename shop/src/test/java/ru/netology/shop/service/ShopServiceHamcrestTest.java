package ru.netology.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.shop.cart.Cart;
import ru.netology.shop.filter.KeywordFilter;
import ru.netology.shop.model.Order;
import ru.netology.shop.model.OrderStatus;
import ru.netology.shop.model.Product;
import ru.netology.shop.repository.Impl.InMemoryOrderRepository;
import ru.netology.shop.repository.Impl.InMemoryProductRepository;
import ru.netology.shop.repository.OrderRepository;
import ru.netology.shop.repository.ProductRepository;
import ru.netology.shop.service.Impl.ConsoleNotificationService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ShopServiceHamcrestTest {

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
        assertThat(filteredProducts, hasSize(2));
    }

    @Test
    public void shouldPlaceOrderSuccessfully() {
        Product product = productRepository.findById(2)
                .orElseThrow(() -> new AssertionError("Продукт с ID=2 не найден"));
        cart.addProduct(product);
        Order order = shopService.placeOrder(cart);
        assertThat(order, is(notNullValue()));
        assertThat(order.getTotalPrice(), is(equalTo(60000.0)));
        assertThat(cart.getItems(), is(empty()));
        assertThat(orderRepository.findById(order.getId()).isPresent(), is(true));
    }

    @Test
    public void filteredListShouldContainSpecificProducts() {
        KeywordFilter filter = new KeywordFilter("pro");
        Product notebook = productRepository.findById(1).orElseThrow();
        Product keyboard = productRepository.findById(5).orElseThrow();
        List<Product> filteredProducts = shopService.filterProducts(filter);
        assertThat(filteredProducts, hasItems(notebook, keyboard));
    }

    @Test
    public void createdOrderShouldHaveCorrectProperties() {
        Product notebook = productRepository.findById(1).orElseThrow();
        cart.addProduct(notebook);
        double expectedPrice = 85000.0;
        Order order = shopService.placeOrder(cart);
        assertThat(order, allOf(
                notNullValue(),
                hasProperty("totalPrice", is(expectedPrice)),
                hasProperty("status", is(OrderStatus.CREATED)),
                hasProperty("products", hasSize(1))
        ));
    }
}