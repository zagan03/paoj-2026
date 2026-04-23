package fooddelivery.models;

public class MenuItem {
    private static int count = 0;
    private final int id;
    private String name;
    private double price;
    private Restaurant restaurant;
    private String category;
    private int preparationTime;
    public MenuItem(String name, double price, Restaurant restaurant, String category, int preparationTime) {
        this.id = ++count;
        this.name = name;
        this.price = (price < 0 ? 0 : price);
        this.restaurant = restaurant;
        this.category = category;
        this.preparationTime = preparationTime;
    }
    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return "[ID " + id + "] " + name + " - " + price + " - Vandut de: " + restaurant.getName();
    }
}
