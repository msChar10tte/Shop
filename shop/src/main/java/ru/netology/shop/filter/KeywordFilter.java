package ru.netology.shop.filter;

import ru.netology.shop.model.Product;

public class KeywordFilter implements Filter {
    private final String keyword;

    public KeywordFilter(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean matches(Product product) {
        return product.name().toLowerCase().contains(keyword) ||
                product.manufacturer().toLowerCase().contains(keyword);
    }
}