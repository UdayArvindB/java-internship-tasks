# Stock Trading Platform (Simulation)
A Java 17 console simulation of a small stock market. This is an educational simulator, not connected to a real exchange and not financial advice.

## Features
- Displays a configurable set of sample stock quotes.
- Buy/sell shares with cash and holdings validation.
- Portfolio valuation, realized transaction history, and unrealized profit/loss.
- Object-oriented model: `Stock`, `Portfolio`, `Trade`, and `TradingService`.
- Persists transactions to `data/trades.csv` when the program exits.

## Run
```bash
mvn clean package
java -cp target/classes com.internship.trading.Main
```
Prices are illustrative sample data and do not update from a live market.
