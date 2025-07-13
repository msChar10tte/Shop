package ru.netology.shop.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.shop.model.Product;

public class CartTest {

    private Cart cart;
    private final Product product1 = new Product(1, "Ноутбук ProBook", 85000.0, "HP");
    private final Product product2 = new Product(5, "Клавиатура Pro", 12000.0, "Logitech");

    @BeforeEach
    public void setup() {
        cart = new Cart();
    }
    @Test
    public void shouldCalculateTotalForMultipleProducts() {
        cart.addProduct(product1);
        cart.addProduct(product2);

        double expectedTotal = 85000.0 + 12000.0;
        Assertions.assertEquals(expectedTotal, cart.calculateTotal());
    }
}