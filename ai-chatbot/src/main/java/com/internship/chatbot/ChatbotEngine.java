package com.internship.chatbot;

import java.util.List;
import java.util.Optional;

public final class ChatbotEngine {
    private final TextProcessor processor = new TextProcessor();
    private final List<Intent> intents = List.of(
        new Intent("greeting", List.of("hello", "hi", "hey", "good morning", "good evening"),
                "Hello! I'm JavaBot. What would you like help with?"),
        new Intent("capabilities", List.of("help", "what can you do", "your features"),
                "I can answer basic FAQs about Java, internships, and how to use this chatbot."),
        new Intent("java", List.of("java", "jvm", "object oriented", "oops"),
                "Java is a class-based, object-oriented language. The JVM executes Java bytecode, helping Java applications run across platforms."),
        new Intent("internship", List.of("internship", "resume", "interview", "project"),
                "For internship preparation, build projects you can explain clearly, keep your README accurate, and be ready to discuss design choices and trade-offs."),
        new Intent("contact", List.of("contact", "support", "email"),
                "This demo has no live support connection. Add your organization's verified contact details here before deployment."),
        new Intent("thanks", List.of("thank you", "thanks"),
                "You're welcome! Feel free to ask another question."),
        new Intent("farewell", List.of("bye", "goodbye", "exit", "quit"),
                "Goodbye! Have a productive day.")
    );

    public String reply(String message) {
        String normalized = processor.normalize(message);
        if (normalized.isBlank()) return "Please type a question so I can try to help.";
        Optional<Intent> match = intents.stream()
                .filter(intent -> intent.phrases().stream().anyMatch(phrase -> matches(normalized, phrase)))
                .findFirst();
        return match.map(Intent::response)
                .orElse("I'm not sure I understood that. Try asking about Java, internships, projects, or type 'help'.");
    }
    private boolean matches(String message, String phrase) {
        String normalizedPhrase = processor.normalize(phrase);
        return message.equals(normalizedPhrase)
                || (" " + message + " ").contains(" " + normalizedPhrase + " ");
    }
}
