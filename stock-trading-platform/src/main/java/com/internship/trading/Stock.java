package com.internship.trading;

public record Stock(String symbol, String name, double price) {
    public Stock {
        if (symbol == null || symbol.isBlank() || name == null || name.isBlank())
            throw new IllegalArgumentException("Stock symbol and name are required.");
        if (!Double.isFinite(price) || price <= 0) throw new IllegalArgumentException("Price must be positive.");
        symbol = symbol.trim().toUpperCase();
        name = name.trim();
    }
}
