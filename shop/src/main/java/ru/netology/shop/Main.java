package ru.netology.shop;

import ru.netology.shop.cart.Cart;
import ru.netology.shop.filter.KeywordFilter;
import ru.netology.shop.model.Product;
import ru.netology.shop.repository.Impl.InMemoryOrderRepository;
import ru.netology.shop.repository.Impl.InMemoryProductRepository;
import ru.netology.shop.repository.OrderRepository;
import ru.netology.shop.repository.ProductRepository;
import ru.netology.shop.service.Impl.ConsoleNotificationService;
import ru.netology.shop.service.NotificationService;
import ru.netology.shop.service.ShopService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static void printMenu() {
        System.out.println("\n===== Меню Магазина =====");
        System.out.println("1. Показать все товары");
        System.out.println("2. Найти товар по ключевому слову");
        System.out.println("3. Добавить товар в корзину по ID");
        System.out.println("4. Показать корзину");
        System.out.println("5. Оформить заказ");
        System.out.println("6. Узнать статус заказа по ID");
        System.out.println("0. Выход");
        System.out.print("Выберите опцию: ");
    }

    public static void main(String[] args) {

        ProductRepository productRepository = new InMemoryProductRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();
        NotificationService notificationService = new ConsoleNotificationService();
        ShopService shopService = new ShopService(productRepository, orderRepository, notificationService);
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("\n--- Доступные товары ---");
                        shopService.getAllProducts().forEach(System.out::println);
                        break;
                    case 2:
                        System.out.print("Введите ключевое слово для поиска: ");
                        String keyword = scanner.nextLine();
                        List<Product> filteredProducts = shopService.filterProducts(new KeywordFilter(keyword));
                        System.out.println("\n--- Результаты поиска ---");
                        if (filteredProducts.isEmpty()) {
                            System.out.println("Товары не найдены.");
                        } else {
                            filteredProducts.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.print("Введите ID товара для добавления в корзину: ");
                        int productId = scanner.nextInt();
                        shopService.findProductById(productId)
                                .ifPresentOrElse(
                                        cart::addProduct,
                                        () -> System.out.println("Товар с ID " + productId + " не найден.")
                                );
                        System.out.println("Товар добавлен в корзину.");
                        break;
                    case 4:
                        System.out.println("\n--- Ваша корзина ---");
                        if (cart.isEmpty()) {
                            System.out.println("Корзина пуста.");
                        } else {
                            cart.getItems().forEach(System.out::println);
                            System.out.printf("-------------------------\nИтого: %.2f руб.\n", cart.calculateTotal());
                        }
                        break;
                    case 5:
                        if (shopService.placeOrder(cart) != null) {
                            System.out.println("Заказ успешно оформлен и передан в обработку.");
                        } else {
                            System.out.println("Не удалось оформить заказ. Корзина пуста.");
                        }
                        break;
                    case 6:
                        System.out.print("Введите ID заказа для проверки статуса: ");
                        int orderId = scanner.nextInt();
                        shopService.findOrderById(orderId)
                                .ifPresentOrElse(
                                        order -> System.out.println("Статус вашего заказа: " + order.getStatus().getDescription()),
                                        () -> System.out.println("Заказ с ID " + orderId + " не найден.")
                                );
                        break;
                    case 0:
                        System.out.println("Спасибо за покупки! До свидания!");
                        return;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка! Пожалуйста, вводите только числа.");
                scanner.next(); // очистка буфера сканера
            }
        }
    }
}