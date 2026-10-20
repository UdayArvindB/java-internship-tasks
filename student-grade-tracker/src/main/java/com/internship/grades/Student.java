package com.internship.grades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Student {
    private final String id;
    private final String name;
    private final List<Double> scores = new ArrayList<>();

    public Student(String id, String name) {
        this.id = requireText(id, "Student ID");
        this.name = requireText(name, "Student name");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " cannot be blank.");
        return value.trim();
    }

    public void addScore(double score) {
        if (!Double.isFinite(score) || score < 0 || score > 100)
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        scores.add(score);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Double> getScores() { return Collections.unmodifiableList(scores); }
    public double average() { return scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0); }
    public double highest() { return scores.stream().mapToDouble(Double::doubleValue).max().orElse(0.0); }
    public double lowest() { return scores.stream().mapToDouble(Double::doubleValue).min().orElse(0.0); }
    public String grade() {
        double avg = average();
        if (scores.isEmpty()) return "N/A";
        if (avg >= 90) return "A";
        if (avg >= 80) return "B";
        if (avg >= 70) return "C";
        if (avg >= 60) return "D";
        return "F";
    }
    @Override public boolean equals(Object other) {
        return other instanceof Student student && id.equalsIgnoreCase(student.id);
    }
    @Override public int hashCode() { return Objects.hash(id.toLowerCase()); }
}
