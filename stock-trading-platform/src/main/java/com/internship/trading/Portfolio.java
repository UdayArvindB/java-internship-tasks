package com.internship.trading;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Portfolio {
    private double cash;
    private final Map<String, Integer> holdings = new LinkedHashMap<>();
    private final Map<String, Double> averageCost = new LinkedHashMap<>();

    public Portfolio(double startingCash) {
        if (!Double.isFinite(startingCash) || startingCash < 0) throw new IllegalArgumentException("Starting cash cannot be negative.");
        cash = startingCash;
    }
    public double cash() { return cash; }
    public Map<String, Integer> holdings() { return Collections.unmodifiableMap(holdings); }
    public int quantity(String symbol) { return holdings.getOrDefault(symbol.toUpperCase(), 0); }
    public double averageCost(String symbol) { return averageCost.getOrDefault(symbol.toUpperCase(), 0.0); }
    void buy(Stock stock, int quantity) {
        double cost = stock.price() * quantity;
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero.");
        if (cost > cash + 1e-9) throw new IllegalArgumentException("Insufficient cash for this purchase.");
        String symbol = stock.symbol();
        int oldQty = this.quantity(symbol), newQty = oldQty + quantity;
        averageCost.put(symbol, ((averageCost(symbol) * oldQty) + cost) / newQty);
        holdings.put(symbol, newQty);
        cash -= cost;
    }
    double sell(Stock stock, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero.");
        String symbol = stock.symbol();
        int oldQty = quantity(symbol);
        if (quantity > oldQty) throw new IllegalArgumentException("You cannot sell more shares than you own.");
        double profit = (stock.price() - averageCost(symbol)) * quantity;
        int remaining = oldQty - quantity;
        if (remaining == 0) { holdings.remove(symbol); averageCost.remove(symbol); }
        else holdings.put(symbol, remaining);
        cash += stock.price() * quantity;
        return profit;
    }
}
