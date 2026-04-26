package com.pao.proiect.fooddelivery.service;

import java.util.*;

import com.pao.proiect.fooddelivery.exception.EmailAlreadyExistsException;
import com.pao.proiect.fooddelivery.exception.IncorrectPhoneNumberException;
import com.pao.proiect.fooddelivery.exception.UserNotFoundException;
import com.pao.proiect.fooddelivery.model.*;
public class UserService {
    private static UserService instance;
    private List<User> registeredUsers;

    private UserService() {
        registeredUsers = new ArrayList<>();
    }
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
            return instance;
        }
        return instance;
    }


    public User searchUserByName(String name) {
        for (User u : registeredUsers) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        throw new UserNotFoundException("Utilizatorul " + name + " nu a fost gasit!");
    }
    public void deleteUserById(int id) {
        boolean removed = registeredUsers.removeIf(user -> user.getId() == id);
        if (removed) {
            System.out.println("Utilizatorul cu ID " + id + " a fost sters cu succes.");
        } else {
            System.out.println("Nu s-a gasit niciun utilizator cu acest ID pentru stergere.");
        }
    }
    public Customer registerCustomer(String name, String email, String phone, String password, Address address, boolean isPremium) {
        validateRegistration(email, phone);
        Customer newCustomer = new Customer(name, email, phone, password, address, isPremium);
        registeredUsers.add(newCustomer);
        System.out.println("Userul " + name + " a fost inregistrat cu succes");
        return newCustomer;
    }
    public Driver registerDriver(String name, String email, String phone, String password, String vehicleNumber) {
        validateRegistration(email, phone);
        Driver newDriver = new Driver(name, email, phone, password, vehicleNumber);
        registeredUsers.add(newDriver);
        System.out.println("Soferul " + name + " a fost inregistrat.");
        return newDriver;
    }
    private void validateRegistration(String email, String phone) {
        for (User u : registeredUsers) {
            if (u.getEmail().equals(email)) {
                throw new EmailAlreadyExistsException("Email-ul " + email + " este deja folosit!");
            }
        }
        if (!phone.matches("[0-9]{10,15}")) { // lungime minim 10 maxim 15
            throw new IncorrectPhoneNumberException("Numarul de telefon este invalid!");
        }
    }

    public List<Driver> getAllDrivers() {
        List<Driver> driversOnly = new ArrayList<>();
        for (User u : registeredUsers) {
            if (u instanceof Driver ) {
                Driver d = (Driver) u;
                driversOnly.add(d);
            }
        }
        return driversOnly;
    }
    public List<Customer> getAllCustomers() {
        List<Customer> customersOnly = new ArrayList<>();
        for (User u : registeredUsers) {
            if (u instanceof Customer ) {
                Customer c = (Customer) u;
                customersOnly.add(c);
            }
        }
        return customersOnly;
    }

}
