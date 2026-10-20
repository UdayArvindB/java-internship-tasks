package com.internship.chatbot;

import java.util.Scanner;

public final class Main {
    private Main() {}
    public static void main(String[] args) {
        ChatbotEngine bot = new ChatbotEngine();
        System.out.println("=== JavaBot FAQ Assistant ===");
        System.out.println("Ask about Java, internships, or projects. Type 'bye' to exit.");
        try (var scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("\nYou: ");
                if (!scanner.hasNextLine()) break;
                String input = scanner.nextLine();
                String reply = bot.reply(input);
                System.out.println("JavaBot: " + reply);
                String normalized = new TextProcessor().normalize(input);
                if (normalized.equals("bye") || normalized.equals("goodbye")
                        || normalized.equals("exit") || normalized.equals("quit")) break;
            }
        }
    }
}
