import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        // Adding employees
        Employee fullTimeEmp = new FullTimeEmployee(1, "Prakul", 3000, 2000);
        fullTimeEmp.assignDepartment("IT");
        employees.add(fullTimeEmp);

        Employee partTimeEmp = new PartTimeEmployee(2, "Parth", 1000, 20, 50);
        partTimeEmp.assignDepartment("HR");
        employees.add(partTimeEmp);

        // Processing and displaying employee details
        for (Employee employee : employees) {
            employee.displayDetails();
        }
    }
}

// Abstract class for employees
abstract class Employee implements Department {
    private int employeeId;
    private String name;
    private double baseSalary;
    private String department;

    public Employee(int employeeId, String name, double baseSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    // Encapsulated fields with getters/setters
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }

    // Assign and get department
    @Override
    public void assignDepartment(String departmentName) { this.department = departmentName; }
    @Override
    public String getDepartmentDetails() {
        return department != null ? "Department: " + department : "No department assigned";
    }

    // Abstract method for salary calculation
    public abstract double calculateSalary();

    // Display employee details
    public void displayDetails() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + name);
        System.out.println("Base Salary: $" + baseSalary);
        System.out.println(getDepartmentDetails());
        System.out.println("Calculated Salary: $" + calculateSalary());
    }
}

// Interface for department-related behavior
interface Department {
    void assignDepartment(String departmentName);
    String getDepartmentDetails();
}

// Full-time employee subclass
class FullTimeEmployee extends Employee {
    private double fixedSalary;

    public FullTimeEmployee(int employeeId, String name, double baseSalary, double fixedSalary) {
        super(employeeId, name, baseSalary);
        this.fixedSalary = fixedSalary;
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + fixedSalary;
    }
}

// Part-time employee subclass
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(int employeeId, String name, double baseSalary, int hoursWorked, double hourlyRate) {
        super(employeeId, name, baseSalary);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + (hoursWorked * hourlyRate);
    }
}

/*
Sample Output:
Employee ID: 1
Name: Prakul
Base Salary: $3000.0
Department: IT
Calculated Salary: $5000.0
Employee ID: 2
Name: Parth
Base Salary: $1000.0
Department: HR
Calculated Salary: $2000.0
*/
