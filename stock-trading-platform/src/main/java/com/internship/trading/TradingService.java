package com.internship.trading;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TradingService {
    private final Map<String, Stock> market;
    private final Portfolio portfolio;
    private final List<Trade> trades = new ArrayList<>();

    public TradingService(Map<String, Stock> market, Portfolio portfolio) {
        this.market = Map.copyOf(market);
        this.portfolio = portfolio;
    }
    public Map<String, Stock> market() { return market; }
    public Portfolio portfolio() { return portfolio; }
    public List<Trade> trades() { return List.copyOf(trades); }
    public void buy(String symbol, int quantity) {
        Stock stock = stock(symbol);
        portfolio.buy(stock, quantity);
        trades.add(new Trade(LocalDateTime.now(), "BUY", stock.symbol(), quantity, stock.price()));
    }
    public void sell(String symbol, int quantity) {
        Stock stock = stock(symbol);
        portfolio.sell(stock, quantity);
        trades.add(new Trade(LocalDateTime.now(), "SELL", stock.symbol(), quantity, stock.price()));
    }
    public double marketValue() {
        return portfolio.holdings().entrySet().stream()
                .mapToDouble(e -> e.getValue() * stock(e.getKey()).price()).sum();
    }
    public double unrealizedProfitLoss() {
        return portfolio.holdings().entrySet().stream()
                .mapToDouble(e -> (stock(e.getKey()).price() - portfolio.averageCost(e.getKey())) * e.getValue()).sum();
    }
    private Stock stock(String symbol) {
        Stock result = market.get(symbol == null ? "" : symbol.trim().toUpperCase());
        if (result == null) throw new IllegalArgumentException("Unknown stock symbol.");
        return result;
    }
}
