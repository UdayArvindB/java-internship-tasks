package com.internship.trading;

import java.time.LocalDateTime;

public record Trade(LocalDateTime timestamp, String side, String symbol, int quantity, double unitPrice) {
    public double total() { return quantity * unitPrice; }
}
