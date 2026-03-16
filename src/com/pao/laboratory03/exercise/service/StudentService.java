package com.pao.laboratory03.exercise.service;
import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;
import java.util.*;
public class StudentService {
    private static StudentService instance;
    private final List<Student> students = new ArrayList<>();

    private StudentService() {}

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                throw new RuntimeException("Studentul '" + name + "' exista deja");
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new StudentNotFoundException("Studentul '" + name + "' nu a fost gasit");
    }

    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu exista studenti.");
            return;
        }
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println((i + 1) + ". " + s);
            for (Map.Entry<Subject, Double> entry : s.getGrades().entrySet()) {
                System.out.println("   " + entry.getKey().name() + " = " + entry.getValue());
            }
        }
    }

    public void printTopStudents() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort((a, b) -> Double.compare(b.getAverage(), a.getAverage()));
        System.out.println("=== Top studenti ===");
        for (int i = 0; i < sorted.size(); i++) {
            Student s = sorted.get(i);
            System.out.printf("%d. %s - media: %.2f%n", i + 1, s.getName(), s.getAverage());
        }
    }
    public void addGrade(String studentName, Subject subject, double grade) {
        Student student = findByName(studentName);
        student.addGrade(subject, grade);
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> result = new HashMap<>();
        for (Subject subject : Subject.values()) {
            double sum = 0;
            int count = 0;
            for (Student s : students) {
                Double grade = s.getGrades().get(subject);
                if (grade != null) {
                    sum += grade;
                    count++;
                }
            }
            if (count > 0) result.put(subject, sum / count);
        }
        return result;
    }
}
