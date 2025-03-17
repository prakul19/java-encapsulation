import java.util.Scanner;

public class RideHailingApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Vehicle vehicle = null;

        try {
            System.out.print("Enter vehicle type (Car/Bike/Auto): ");
            String vehicleType = scanner.nextLine();

            System.out.print("Enter vehicle ID: ");
            String vehicleId = scanner.nextLine();

            System.out.print("Enter driver name: ");
            String driverName = scanner.nextLine();

            System.out.print("Enter rate per kilometer: ");
            double ratePerKm = scanner.nextDouble();

            System.out.print("Enter travel distance (in kilometers): ");
            double distance = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            switch (vehicleType.toLowerCase()) {
                case "car":
                    vehicle = new Car(vehicleId, driverName, ratePerKm);
                    break;
                case "bike":
                    vehicle = new Bike(vehicleId, driverName, ratePerKm);
                    break;
                case "auto":
                    vehicle = new Auto(vehicleId, driverName, ratePerKm);
                    break;
                default:
                    System.out.println("Invalid vehicle type! Please try again.");
                    return;
            }

            // Display vehicle details and calculate fare dynamically
            vehicle.getVehicleDetails();
            System.out.println("Fare for " + distance + " km: $" + vehicle.calculateFare(distance));
            if (vehicle instanceof GPS) {
                GPS gpsVehicle = (GPS) vehicle;
                System.out.println("Current Location: " + gpsVehicle.getCurrentLocation());
                gpsVehicle.updateLocation("New Location XYZ");
            }

        } finally {
            scanner.close();
        }
    }
}

// Abstract class Vehicle
abstract class Vehicle implements GPS {
    private String vehicleId;
    private String driverName;
    private double ratePerKm;

    public Vehicle(String vehicleId, String driverName, double ratePerKm) {
        this.vehicleId = vehicleId;
        this.driverName = driverName;
        this.ratePerKm = ratePerKm;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getDriverName() {
        return driverName;
    }

    public double getRatePerKm() {
        return ratePerKm;
    }

    public void getVehicleDetails() {
        System.out.println("Vehicle ID: " + vehicleId);
        System.out.println("Driver Name: " + driverName);
        System.out.println("Rate per Kilometer: $" + ratePerKm);
    }

    public abstract double calculateFare(double distance);
}

// Interface GPS
interface GPS {
    String getCurrentLocation();
    void updateLocation(String newLocation);
}

// Car subclass
class Car extends Vehicle {
    private String currentLocation = "Default Location";

    public Car(String vehicleId, String driverName, double ratePerKm) {
        super(vehicleId, driverName, ratePerKm);
    }

    @Override
    public double calculateFare(double distance) {
        return getRatePerKm() * distance + 10; // Base rate plus fixed $10 additional charge
    }

    @Override
    public String getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void updateLocation(String newLocation) {
        currentLocation = newLocation;
        System.out.println("Car location updated to: " + currentLocation);
    }
}

// Bike subclass
class Bike extends Vehicle {
    private String currentLocation = "Default Location";

    public Bike(String vehicleId, String driverName, double ratePerKm) {
        super(vehicleId, driverName, ratePerKm);
    }

    @Override
    public double calculateFare(double distance) {
        return getRatePerKm() * distance; // No additional charges for bikes
    }

    @Override
    public String getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void updateLocation(String newLocation) {
        currentLocation = newLocation;
        System.out.println("Bike location updated to: " + currentLocation);
    }
}

// Auto subclass
class Auto extends Vehicle {
    private String currentLocation = "Default Location";

    public Auto(String vehicleId, String driverName, double ratePerKm) {
        super(vehicleId, driverName, ratePerKm);
    }

    @Override
    public double calculateFare(double distance) {
        return getRatePerKm() * distance + 5; // Base rate plus fixed $5 additional charge
    }

    @Override
    public String getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void updateLocation(String newLocation) {
        currentLocation = newLocation;
        System.out.println("Auto location updated to: " + currentLocation);
    }
}

/*
Sample Input:
Enter vehicle type (Car/Bike/Auto): Car
Enter vehicle ID: V001
Enter driver name: prakul
Enter rate per kilometer: 10
Enter travel distance (in kilometers): 15

Sample Output:
Vehicle ID: V001
Driver Name: prakul
Rate per Kilometer: $10.0
Fare for 15.0 km: $160.0
Current Location: Default Location
Car location updated to: New Location XYZ
*/
