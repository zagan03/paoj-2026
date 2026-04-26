package com.pao.proiect.fooddelivery.model;
import com.pao.proiect.fooddelivery.exception.ItemsFromDifferentRestaurantsException;

import java.util.List;
import java.util.ArrayList;
public class Customer extends User
{
    private Address address;
    private boolean isPremium;
    private List<MenuItem> cart;
    public Customer(String name, String email, String phoneNumber,
                    String password, Address address, boolean isPremium) {
        super(name, email, phoneNumber, password);
        this.address = address;
        this.isPremium = isPremium;
        this.cart = new ArrayList<>();
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public boolean isPremium() { // getter
        return isPremium;
    }
    public void setIsPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }


    public void addToCart(MenuItem item) {
        if (!cart.isEmpty()) {
            if (!cart.get(0).getRestaurant().equals(item.getRestaurant())) {
                throw new ItemsFromDifferentRestaurantsException("Ai deja produse in cos de la alt restaurant. Goleste cosul sau incepe o noua comanda!");
            }
        }
        cart.add(item);
    }
    public void removeFromCart(MenuItem item) {
        if(cart.isEmpty()) {
            System.out.println("Nu exista produse de eliminat!");
            return;
        }
        cart.remove(item);
        System.out.println("Produsul " + item.getName() + " a fost eliminat din cos!");
    }
    public void clearCart() {
        cart.clear();
        System.out.println("Cosul a fost golit!");
    }
    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
    public List<MenuItem> getCartItems() {
        return new ArrayList<>(cart);
    }

    @Override
    public String toString() {
        return "Clientul cu " + super.toString() + address + " Status: " + (isPremium ? "Premium" : "Standard");
    }
    @Override
    public String getRole() {
        return "CUSTOMER";
    }
}
