package com.internship.grades;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class GradeService {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (students.contains(student)) throw new IllegalArgumentException("A student with that ID already exists.");
        students.add(student);
    }
    public List<Student> allStudents() { return List.copyOf(students); }
    public Optional<Student> findById(String id) {
        return students.stream().filter(s -> s.getId().equalsIgnoreCase(id.trim())).findFirst();
    }
    public Optional<Student> topStudent() {
        return students.stream().filter(s -> !s.getScores().isEmpty()).max(Comparator.comparingDouble(Student::average));
    }
    public Optional<Student> lowestStudent() {
        return students.stream().filter(s -> !s.getScores().isEmpty()).min(Comparator.comparingDouble(Student::average));
    }
    public double classAverage() {
        return students.stream().filter(s -> !s.getScores().isEmpty()).mapToDouble(Student::average).average().orElse(0.0);
    }
}
