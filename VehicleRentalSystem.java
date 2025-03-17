import java.util.ArrayList;
import java.util.List;

public class VehicleRentalSystem {
    public static void main(String[] args) {
        List<Vehicle> vehicles = new ArrayList<>();

        // Adding vehicles to the list
        vehicles.add(new Car("C001", "Car", 500, "CAR-123"));
        vehicles.add(new Bike("B001", "Bike", 300, "BIKE-456"));
        vehicles.add(new Truck("T001", "Truck", 1000, "TRUCK-789"));

        // Iterating over the list and calculating costs
        for (Vehicle vehicle : vehicles) {
            vehicle.displayDetails();
            System.out.println("Rental Cost for 5 days: $" + vehicle.calculateRentalCost(5));
            System.out.println(vehicle.getInsuranceDetails());
            System.out.println("Insurance Cost: $" + vehicle.calculateInsurance());
        }
    }
}

// Abstract class for Vehicle
abstract class Vehicle implements Insurable {
    private String vehicleNumber;
    private String type;
    private double rentalRate;

    public Vehicle(String vehicleNumber, String type, double rentalRate) {
        this.vehicleNumber = vehicleNumber;
        this.type = type;
        this.rentalRate = rentalRate;
    }

    // Encapsulated fields with getters and setters
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getRentalRate() { return rentalRate; }
    public void setRentalRate(double rentalRate) { this.rentalRate = rentalRate; }

    // Abstract method to calculate rental cost
    public abstract double calculateRentalCost(int days);

    // Display vehicle details
    public void displayDetails() {
        System.out.println("Vehicle Number: " + vehicleNumber);
        System.out.println("Type: " + type);
        System.out.println("Rental Rate per Day: $" + rentalRate);
    }
}

// Interface for insurance-related behavior
interface Insurable {
    double calculateInsurance();
    String getInsuranceDetails();
}

// Car subclass
class Car extends Vehicle {
    private String policyNumber; // Encapsulated insurance policy number

    public Car(String vehicleNumber, String type, double rentalRate, String policyNumber) {
        super(vehicleNumber, type, rentalRate);
        this.policyNumber = policyNumber;
    }

    @Override
    public double calculateRentalCost(int days) {
        return getRentalRate() * days;
    }

    @Override
    public double calculateInsurance() {
        return getRentalRate() * 0.1;
    }

    @Override
    public String getInsuranceDetails() {
        return "Insurance Policy Number: " + policyNumber;
    }
}

// Bike subclass
class Bike extends Vehicle {
    private String policyNumber;

    public Bike(String vehicleNumber, String type, double rentalRate, String policyNumber) {
        super(vehicleNumber, type, rentalRate);
        this.policyNumber = policyNumber;
    }

    @Override
    public double calculateRentalCost(int days) {
        return getRentalRate() * days;
    }

    @Override
    public double calculateInsurance() {
        return getRentalRate() * 0.05;
    }

    @Override
    public String getInsuranceDetails() {
        return "Insurance Policy Number: " + policyNumber;
    }
}

// Truck subclass
class Truck extends Vehicle {
    private String policyNumber;

    public Truck(String vehicleNumber, String type, double rentalRate, String policyNumber) {
        super(vehicleNumber, type, rentalRate);
        this.policyNumber = policyNumber;
    }

    @Override
    public double calculateRentalCost(int days) {
        return getRentalRate() * days * 1.2;
    }

    @Override
    public double calculateInsurance() {
        return getRentalRate() * 0.2;
    }

    @Override
    public String getInsuranceDetails() {
        return "Insurance Policy Number: " + policyNumber;
    }
}

/*
Sample Output:
Vehicle Number: C001
Type: Car
Rental Rate per Day: $500.0
Rental Cost for 5 days: $2500.0
Insurance Policy Number: CAR-123
Insurance Cost: $50.0

Vehicle Number: B001
Type: Bike
Rental Rate per Day: $300.0
Rental Cost for 5 days: $1500.0
Insurance Policy Number: BIKE-456
Insurance Cost: $15.0

Vehicle Number: T001
Type: Truck
Rental Rate per Day: $1000.0
Rental Cost for 5 days: $6000.0
Insurance Policy Number: TRUCK-789
Insurance Cost: $200.0
*/
