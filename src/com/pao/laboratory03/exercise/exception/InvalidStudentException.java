package com.pao.laboratory03.exercise.exception;

public class InvalidStudentException extends RuntimeException {
    public InvalidStudentException(String message) {
        super(message); // Aici nu ar trebui sa mai dea eroare
    }
}
