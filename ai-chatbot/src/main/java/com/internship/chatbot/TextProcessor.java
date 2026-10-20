package com.internship.chatbot;

import java.util.Locale;

public final class TextProcessor {
    public String normalize(String input) {
        if (input == null) return "";
        return input.toLowerCase(Locale.ROOT)
                .replaceAll("[^\\p{L}\\p{N}\\s']", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
