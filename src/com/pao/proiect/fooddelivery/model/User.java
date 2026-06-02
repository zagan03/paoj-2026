package com.pao.proiect.fooddelivery.model;

import java.util.Objects;

public abstract class User {
    private static int count = 0;
    protected int id;
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected double rating;
    public User(String name, String email, String phoneNumber, String password) {
        this.id = ++count;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.rating = 0.0;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
            return id;
        }
        public void setId(int id)
        {this.id = id;}

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public double getRating() {
            return rating;
        }
        public abstract String getRole();
        @Override
    public String toString() {
        return "ID: " +  id + " | Nume: "  + name + " | Email: " + email + " | Rating: " + rating;
        }
        @Override
    public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            User user = (User) obj;
            return (user.id == this.id);
    }
        @Override
    public int hashCode() {
            return Objects.hash(id);
        }

}
