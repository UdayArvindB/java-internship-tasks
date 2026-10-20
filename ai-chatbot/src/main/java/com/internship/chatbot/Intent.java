package com.internship.chatbot;

import java.util.List;

public record Intent(String name, List<String> phrases, String response) {
    public Intent {
        phrases = List.copyOf(phrases);
    }
}
