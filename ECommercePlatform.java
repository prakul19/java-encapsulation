import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ECommercePlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = new ArrayList<>();

        try {
            System.out.print("Enter the number of products: ");
            int n = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < n; i++) {
                System.out.println("Enter product type (Electronics/Clothing/Groceries): ");
                String type = scanner.nextLine();

                System.out.print("Enter product ID: ");
                int productId = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter product name: ");
                String name = scanner.nextLine();

                System.out.print("Enter product price: ");
                double price = scanner.nextDouble();
                scanner.nextLine();

                switch (type.toLowerCase()) {
                    case "electronics":
                        System.out.print("Enter warranty period (in years): ");
                        int warranty = scanner.nextInt();
                        scanner.nextLine();
                        products.add(new Electronics(productId, name, price, warranty));
                        break;

                    case "clothing":
                        System.out.print("Enter brand name: ");
                        String brand = scanner.nextLine();
                        products.add(new Clothing(productId, name, price, brand));
                        break;

                    case "groceries":
                        System.out.print("Enter expiry period (in days): ");
                        int expiryDays = scanner.nextInt();
                        scanner.nextLine();
                        products.add(new Groceries(productId, name, price, expiryDays));
                        break;

                    default:
                        System.out.println("Invalid product type! Skipping.");
                }
            }

            // Calculate and print final prices for all products
            for (Product product : products) {
                product.displayDetails();
                System.out.println("Final Price: $" + product.calculateFinalPrice());
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

// Abstract class for Product
abstract class Product implements Taxable {
    private int productId;
    private String name;
    private double price;

    public Product(int productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    // Encapsulated fields with getters and setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // Abstract method for discount calculation
    public abstract double calculateDiscount();

    // Calculate final price
    public double calculateFinalPrice() {
        return price + calculateTax() - calculateDiscount();
    }

    // Display product details
    public void displayDetails() {
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
        System.out.println(getTaxDetails());
    }
}

// Interface for taxable behavior
interface Taxable {
    double calculateTax();
    String getTaxDetails();
}

// Electronics subclass
class Electronics extends Product {
    private int warranty;

    public Electronics(int productId, String name, double price, int warranty) {
        super(productId, name, price);
        this.warranty = warranty;
    }

    @Override
    public double calculateDiscount() {
        return getPrice() * 0.1; // 10% discount
    }

    @Override
    public double calculateTax() {
        return getPrice() * 0.18; // 18% tax
    }

    @Override
    public String getTaxDetails() {
        return "Electronics Tax: 18%";
    }
}

// Clothing subclass
class Clothing extends Product {
    private String brand;

    public Clothing(int productId, String name, double price, String brand) {
        super(productId, name, price);
        this.brand = brand;
    }

    @Override
    public double calculateDiscount() {
        return getPrice() * 0.05; // 5% discount
    }

    @Override
    public double calculateTax() {
        return getPrice() * 0.12; // 12% tax
    }

    @Override
    public String getTaxDetails() {
        return "Clothing Tax: 12%";
    }
}

// Groceries subclass
class Groceries extends Product {
    private int expiryDays;

    public Groceries(int productId, String name, double price, int expiryDays) {
        super(productId, name, price);
        this.expiryDays = expiryDays;
    }

    @Override
    public double calculateDiscount() {
        return expiryDays < 7 ? getPrice() * 0.2 : getPrice() * 0.1;
    }

    @Override
    public double calculateTax() {
        return 0;
    }

    @Override
    public String getTaxDetails() {
        return "Groceries Tax: 0% (Exempted)";
    }
}

/*
Sample Input and Output:
Enter the number of products: 2
Enter product type (Electronics/Clothing/Groceries): Electronics
Enter product ID: 101
Enter product name: Laptop
Enter product price: 50000
Enter warranty period (in years): 2

Product ID: 101
Name: Laptop
Price: $50000.0
Electronics Tax: 18%
Final Price: $56500.0

Enter product type (Electronics/Clothing/Groceries): Groceries
Enter product ID: 102
Enter product name: Milk
Enter product price: 50
Enter expiry period (in days): 5

Product ID: 102
Name: Milk
Price: $50.0
Groceries Tax: 0% (Exempted)
Final Price: $40.0
*/
