package com.pao.laboratory03.bonus.model;

public enum Priority {
    LOW(1.0), MEDIUM(1.5), HIGH(2.0), CRITICAL(3.0);

    private final double factor;

    Priority(double factor) { this.factor = factor; }

    public double calculateScore(int baseDays) {
        return baseDays * factor;
    }
}