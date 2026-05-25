DROP TABLE IF EXISTS OrderItems;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS MenuItems;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Restaurants;
DROP TABLE IF EXISTS Addresses;

create table Addresses (
    AddressID int auto_increment primary key,
    City varchar(60) not null,
    Street varchar(100) not null,
    Number varchar(10) not null,
    Details varchar(255) not null
);
CREATE TABLE Restaurants (
    RestaurantID INT AUTO_INCREMENT PRIMARY KEY,
    Name varchar(255) not null,
    Rating double,
    IsOpen boolean,
    AddressID int,
    constraint fk_Rest_Address
    foreign key (AddressID)
    references Addresses(AddressID)
);

create table MenuItems (
    ItemID int auto_increment primary key,
    Name varchar(255) not null,
    Price double not null,
    Category varchar(100),
    PreparationTime int,
    RestaurantID int,
    constraint fk_Restaurant
    foreign key (RestaurantID)
    references Restaurants(RestaurantID)
);


CREATE TABLE Users (
    UserID int auto_increment primary key,
    Role varchar(20) not null, -- aici salvam daca este CUSTOMER sau DRIVER
    Name varchar(100) not null,
    Email varchar(100),
    PhoneNumber varchar(20) not null,
    Password varchar(100) not null,
    Rating double default 0.0,

    AddressID int,
    constraint fk_Address
    foreign key (AddressID)
    references Addresses(AddressID),
    IsPremium boolean default FALSE,
    VehicleNumber varchar(15)
);

Create table Orders (
    OrderID int auto_increment primary key,
    TotalPrice DOUBLE NOT NULL,
    Status ENUM('PLACED', 'PREPARING', 'IN_DELIVERY', 'DELIVERED', 'CANCELED') DEFAULT 'PLACED',
    PaymentMethod ENUM('CASH', 'CARD', 'ONLINE_WALLET') NOT NULL,
    OrderDate DATETIME NOT NULL,
    CustomerID INT NOT NULL,
    RestaurantID INT NOT NULL,
    CONSTRAINT fk_Order_Customer FOREIGN KEY (CustomerID) REFERENCES Users(UserID),
    CONSTRAINT fk_Order_Restaurant FOREIGN KEY (RestaurantID) REFERENCES Restaurants(RestaurantID),

    DriverID INT,
    CONSTRAINT fk_Order_Driver FOREIGN KEY (DriverID) REFERENCES Users(UserID)
);

CREATE TABLE OrderItems (

    OrderID INT NOT NULL,
    ItemID INT NOT NULL,
    Quantity INT NOT NULL DEFAULT 1,

    PRIMARY KEY (OrderID, ItemID),
    CONSTRAINT fk_OrderItem_Order FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    CONSTRAINT fk_OrderItem_Item FOREIGN KEY (ItemID) REFERENCES MenuItems(ItemID)
)ENGINE=InnoDB;

