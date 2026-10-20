package com.internship.trading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public final class Main {
    private static final Path TRADE_FILE = Path.of("data", "trades.csv");
    private Main() {}
    public static void main(String[] args) {
        Map<String, Stock> market = new LinkedHashMap<>();
        for (Stock s : new Stock[] { new Stock("TCS", "Tata Consultancy Services", 3850),
                new Stock("INFY", "Infosys", 1780), new Stock("RELIANCE", "Reliance Industries", 2920),
                new Stock("HDFCBANK", "HDFC Bank", 1680) }) market.put(s.symbol(), s);
        var service = new TradingService(market, new Portfolio(100_000));
        try (var in = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n=== Stock Trading Simulator ===\n1. Market quotes  2. Buy  3. Sell  4. Portfolio  5. Trade history  0. Exit");
                System.out.print("Choose: "); String choice = in.nextLine().trim();
                try {
                    switch (choice) {
                        case "1" -> showMarket(service);
                        case "2" -> trade(in, service, true);
                        case "3" -> trade(in, service, false);
                        case "4" -> showPortfolio(service);
                        case "5" -> service.trades().forEach(System.out::println);
                        case "0" -> running = false;
                        default -> System.out.println("Choose a valid menu option.");
                    }
                } catch (IllegalArgumentException ex) { System.out.println("Trade rejected: " + ex.getMessage()); }
            }
        }
        try { saveTrades(service); System.out.println("Trade history saved to " + TRADE_FILE); }
        catch (IOException ex) { System.err.println("Could not save trade history: " + ex.getMessage()); }
    }
    private static void showMarket(TradingService s) {
        System.out.println("\nSYMBOL      COMPANY                         PRICE (INR)");
        s.market().values().forEach(x -> System.out.printf("%-11s %-30s ₹%,.2f%n", x.symbol(), x.name(), x.price()));
    }
    private static void trade(Scanner in, TradingService s, boolean buy) {
        System.out.print("Symbol: "); String symbol = in.nextLine();
        System.out.print("Quantity: "); int qty = Integer.parseInt(in.nextLine().trim());
        if (buy) s.buy(symbol, qty); else s.sell(symbol, qty);
        System.out.println((buy ? "Purchase" : "Sale") + " completed.");
    }
    private static void showPortfolio(TradingService s) {
        var p = s.portfolio();
        System.out.printf("%nCash: ₹%,.2f%nHoldings market value: ₹%,.2f%nTotal value: ₹%,.2f%nUnrealized P/L: ₹%,.2f%n",
                p.cash(), s.marketValue(), p.cash() + s.marketValue(), s.unrealizedProfitLoss());
        p.holdings().forEach((symbol, qty) -> System.out.printf("%s: %d shares (avg cost ₹%.2f)%n", symbol, qty, p.averageCost(symbol)));
    }
    private static void saveTrades(TradingService s) throws IOException {
        Files.createDirectories(TRADE_FILE.getParent());
        boolean exists = Files.exists(TRADE_FILE);
        try (var writer = Files.newBufferedWriter(TRADE_FILE, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (!exists || Files.size(TRADE_FILE) == 0) writer.write("timestamp,side,symbol,quantity,unit_price,total\n");
            for (Trade t : s.trades()) writer.write(String.format("%s,%s,%s,%d,%.2f,%.2f%n",
                    t.timestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), t.side(), t.symbol(), t.quantity(), t.unitPrice(), t.total()));
        }
    }
}
