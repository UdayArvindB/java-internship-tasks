package com.internship.grades;

import java.util.Scanner;

public final class Main {
    private Main() {}
    public static void main(String[] args) {
        var service = new GradeService();
        try (var scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n=== Student Grade Tracker ===");
                System.out.println("1. Add student  2. Add score  3. Student report  4. Class report  0. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine().trim();
                try {
                    switch (choice) {
                        case "1" -> addStudent(scanner, service);
                        case "2" -> addScore(scanner, service);
                        case "3" -> studentReport(scanner, service);
                        case "4" -> classReport(service);
                        case "0" -> running = false;
                        default -> System.out.println("Please choose one of the listed options.");
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("Unable to complete request: " + ex.getMessage());
                }
            }
        }
        System.out.println("Goodbye.");
    }
    private static void addStudent(Scanner in, GradeService service) {
        System.out.print("Student ID: "); String id = in.nextLine();
        System.out.print("Student name: "); String name = in.nextLine();
        service.addStudent(new Student(id, name));
        System.out.println("Student registered.");
    }
    private static void addScore(Scanner in, GradeService service) {
        System.out.print("Student ID: "); String id = in.nextLine();
        Student student = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Student ID not found."));
        System.out.print("Score (0-100): ");
        double score = Double.parseDouble(in.nextLine().trim());
        student.addScore(score);
        System.out.println("Score recorded.");
    }
    private static void studentReport(Scanner in, GradeService service) {
        System.out.print("Student ID: ");
        Student s = service.findById(in.nextLine()).orElseThrow(() -> new IllegalArgumentException("Student ID not found."));
        System.out.printf("%nID: %s | Name: %s%nScores: %s%nAverage: %.2f | Highest: %.2f | Lowest: %.2f | Grade: %s%n",
                s.getId(), s.getName(), s.getScores(), s.average(), s.highest(), s.lowest(), s.grade());
    }
    private static void classReport(GradeService service) {
        if (service.allStudents().isEmpty()) { System.out.println("No students registered yet."); return; }
        System.out.println("\n--- Class Summary ---");
        System.out.printf("%-12s %-22s %-10s %-8s%n", "ID", "NAME", "AVERAGE", "GRADE");
        for (Student s : service.allStudents())
            System.out.printf("%-12s %-22s %-10.2f %-8s%n", s.getId(), s.getName(), s.average(), s.grade());
        System.out.printf("Class average: %.2f%n", service.classAverage());
        service.topStudent().ifPresent(s -> System.out.printf("Highest average: %s (%.2f)%n", s.getName(), s.average()));
        service.lowestStudent().ifPresent(s -> System.out.printf("Lowest average: %s (%.2f)%n", s.getName(), s.average()));
    }
}
