package com.pao.laboratory03.enums;

public enum Priority {
    // Definirea constantelor cu implementarea metodei abstracte pentru emoji
    LOW(1, "green") {
        @Override
        public String getEmoji() { return "🟢"; }
    },
    MEDIUM(2, "yellow") {
        @Override
        public String getEmoji() { return "🟡"; }
    },
    HIGH(3, "orange") {
        @Override
        public String getEmoji() { return "🟠"; }
    },
    CRITICAL(4, "red") {
        @Override
        public String getEmoji() { return "🔴"; }
    };

    // Campuri private pentru a stoca datele fiecărei constante
    private final int level;
    private final String color;

    // Constructorul (este privat implicit in enum-uri)
    Priority(int level, String color) {
        this.level = level;
        this.color = color;
    }

    // Getteri pentru a accesa datele din exterior
    public int getLevel() { return level; }
    public String getColor() { return color; }

    // Metoda abstracta pe care fiecare constanta trebuie sa o implementeze
    public abstract String getEmoji();
}