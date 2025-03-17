import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<LibraryItem> items = new ArrayList<>();

        try {
            System.out.print("Enter the number of library items: ");
            int numberOfItems = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numberOfItems; i++) {
                System.out.println("Enter details for item " + (i + 1) + ":");
                System.out.print("Enter item type (Book/Magazine/DVD): ");
                String itemType = scanner.nextLine();

                System.out.print("Enter item ID: ");
                String itemId = scanner.nextLine();

                System.out.print("Enter title: ");
                String title = scanner.nextLine();

                System.out.print("Enter author: ");
                String author = scanner.nextLine();

                // Create the appropriate item based on the type
                switch (itemType.toLowerCase()) {
                    case "book":
                        items.add(new Book(itemId, title, author));
                        break;
                    case "magazine":
                        items.add(new Magazine(itemId, title, author));
                        break;
                    case "dvd":
                        items.add(new DVD(itemId, title, author));
                        break;
                    default:
                        System.out.println("Invalid item type! Skipping...");
                }
            }

            // Process and display details for each library item
            for (LibraryItem item : items) {
                item.getItemDetails();
                System.out.println("Loan Duration: " + item.getLoanDuration() + " days");
                if (item instanceof Reservable) {
                    Reservable reservableItem = (Reservable) item;
                    reservableItem.reserveItem();
                    System.out.println(reservableItem.checkAvailability());
                }
            }
        } finally {
            scanner.close();
        }
    }
}

// Abstract class for general library items
abstract class LibraryItem {
    private String itemId;
    private String title;
    private String author;

    public LibraryItem(String itemId, String title, String author) {
        this.itemId = itemId;
        this.title = title;
        this.author = author;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    // Display basic details of the item
    public void getItemDetails() {
        System.out.println("Item ID: " + itemId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
    }

    // Abstract method for calculating loan duration
    public abstract int getLoanDuration();
}

// Interface for reservable items
interface Reservable {
    void reserveItem();
    String checkAvailability();
}

// Book subclass with reservation functionality
class Book extends LibraryItem implements Reservable {
    private boolean reserved = false;

    public Book(String itemId, String title, String author) {
        super(itemId, title, author);
    }

    @Override
    public int getLoanDuration() {
        return 14; // Loan duration specific to books
    }

    @Override
    public void reserveItem() {
        reserved = true;
        System.out.println("The book has been reserved.");
    }

    @Override
    public String checkAvailability() {
        return reserved ? "The book is reserved." : "The book is available.";
    }
}

// Magazine subclass without reservation functionality
class Magazine extends LibraryItem {
    public Magazine(String itemId, String title, String author) {
        super(itemId, title, author);
    }

    @Override
    public int getLoanDuration() {
        return 7; // Loan duration specific to magazines
    }
}

// DVD subclass with reservation functionality
class DVD extends LibraryItem implements Reservable {
    private boolean reserved = false;

    public DVD(String itemId, String title, String author) {
        super(itemId, title, author);
    }

    @Override
    public int getLoanDuration() {
        return 5; // Loan duration specific to DVDs
    }

    @Override
    public void reserveItem() {
        reserved = true;
        System.out.println("The DVD has been reserved.");
    }

    @Override
    public String checkAvailability() {
        return reserved ? "The DVD is reserved." : "The DVD is available.";
    }
}

/*
Input:
Enter the number of library items: 3
Enter details for item 1:
Enter item type (Book/Magazine/DVD): Book
Enter item ID: B001
Enter title: Manifestation
Enter author: Roxie Nafousi
Enter details for item 2:
Enter item type (Book/Magazine/DVD): Magazine
Enter item ID: M001
Enter title: National Geographic
Enter author: Various Authors
Enter details for item 3:
Enter item type (Book/Magazine/DVD): DVD
Enter item ID: D001
Enter title: Interstellar
Enter author: Christopher Nolan

Output:
Item ID: B001
Title: Manifestation
Author: Roxie Nafousi
Loan Duration: 14 days
The book has been reserved.
The book is reserved.
Item ID: M001
Title: National Geographic
Author: Various Authors
Loan Duration: 7 days
Item ID: D001
Title: Interstellar
Author: Christopher Nolan
Loan Duration: 5 days
The DVD has been reserved.
The DVD is reserved.
*/
