import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OnlineFoodDeliverySystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<FoodItem> order = new ArrayList<>();

        try {
            System.out.print("Enter the number of food items to order: ");
            int numberOfItems = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numberOfItems; i++) {
                System.out.println("Enter details for item " + (i + 1) + ":");
                System.out.print("Enter item type (Veg/NonVeg): ");
                String itemType = scanner.nextLine();

                System.out.print("Enter item name : ");
                String itemName = scanner.nextLine();

                System.out.print("Enter price per unit: ");
                double price = scanner.nextDouble();

                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                if (itemType.equalsIgnoreCase("Veg")) {
                    order.add(new VegItem(itemName, price, quantity));
                } else if (itemType.equalsIgnoreCase("NonVeg")) {
                    order.add(new NonVegItem(itemName, price, quantity));
                } else {
                    System.out.println("Invalid item type! Skipping...");
                }
            }

            for (FoodItem item : order) {
                item.getItemDetails();
                System.out.println("Total Price: " + item.calculateTotalPrice());
                if (item instanceof Discountable) {
                    Discountable discountableItem = (Discountable) item;
                    discountableItem.applyDiscount();
                    System.out.println(discountableItem.getDiscountDetails());
                }
            }
        } finally {
            scanner.close();
        }
    }
}

// Abstract class FoodItem
abstract class FoodItem {
    private String itemName;
    private double price;
    private int quantity;

    public FoodItem(String itemName, double price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void getItemDetails() {
        System.out.println("Item Name: " + itemName);
        System.out.println("Price per Unit: $" + price);
        System.out.println("Quantity: " + quantity);
    }

    public abstract double calculateTotalPrice();
}

// Interface for discountable items
interface Discountable {
    void applyDiscount();
    String getDiscountDetails();
}

// VegItem subclass
class VegItem extends FoodItem implements Discountable {
    private boolean discountApplied = false;
    private double discountAmount = 0.0;

    public VegItem(String itemName, double price, int quantity) {
        super(itemName, price, quantity);
    }

    @Override
    public double calculateTotalPrice() {
        return getPrice() * getQuantity();
    }

    @Override
    public void applyDiscount() {
        if (!discountApplied) {
            discountAmount = calculateTotalPrice() * 0.1; // 10% discount for veg items
            discountApplied = true;
        }
    }

    @Override
    public String getDiscountDetails() {
        return discountApplied ? "Discount Applied: $" + discountAmount : "No discount applied.";
    }
}

// NonVegItem subclass
class NonVegItem extends FoodItem implements Discountable {
    private boolean discountApplied = false;
    private double discountAmount = 0.0;

    public NonVegItem(String itemName, double price, int quantity) {
        super(itemName, price, quantity);
    }

    @Override
    public double calculateTotalPrice() {
        double basePrice = getPrice() * getQuantity();
        double additionalCharge = basePrice * 0.15; // 15% additional charge for non-veg items
        return basePrice + additionalCharge;
    }

    @Override
    public void applyDiscount() {
        if (!discountApplied) {
            discountAmount = calculateTotalPrice() * 0.05; // 5% discount for non-veg items
            discountApplied = true;
        }
    }

    @Override
    public String getDiscountDetails() {
        return discountApplied ? "Discount Applied: $" + discountAmount : "No discount applied.";
    }
}

/*
Input:
Enter the number of food items to order: 3
Enter details for item 1:
Enter item type (Veg/NonVeg): Veg
Enter item name : Naan Paneer
Enter price per unit: 200
Enter quantity: 2
Enter details for item 2:
Enter item type (Veg/NonVeg): Veg
Enter item name : Chole Bhatoore
Enter price per unit: 150
Enter quantity: 1
Enter details for item 3:
Enter item type (Veg/NonVeg): NonVeg
Enter item name : Butter Chicken
Enter price per unit: 300
Enter quantity: 1

Output:
Item Name: Naan Paneer
Price per Unit: 200.0
Quantity: 2
Total Price: 400.0
Discount Applied: 40.0
Item Name: Chole Bhatoore
Price per Unit: 150.0
Quantity: 1
Total Price: 150.0
Discount Applied: 15.0
Item Name: Butter Chicken
Price per Unit: 300.0
Quantity: 1
Total Price: 345.0
Discount Applied: 17.25
*/
