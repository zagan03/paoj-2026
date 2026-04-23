package fooddelivery.models;

public abstract class User {
    private static int count = 0;
    protected final int id;
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
        public int getId() {
            return id;
        }
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
        @Override
    public String toString() {
        return "ID: " +  id + " | Nume: "  + name + " | Email: " + email + " | Rating: " + rating;
        }
}
