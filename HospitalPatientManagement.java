import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HospitalPatientManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Patient> patients = new ArrayList<>();

        try {
            System.out.print("Enter the number of patients: ");
            int numberOfPatients = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            for (int i = 0; i < numberOfPatients; i++) {
                System.out.println("Enter details for patient " + (i + 1) + ":");

                System.out.print("Enter patient type (InPatient/OutPatient): ");
                String patientType = scanner.nextLine();

                System.out.print("Enter patient ID: ");
                String patientId = scanner.nextLine();

                System.out.print("Enter name: ");
                String name = scanner.nextLine();

                System.out.print("Enter age: ");
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                System.out.print("Enter diagnosis: ");
                String diagnosis = scanner.nextLine();

                if (patientType.equalsIgnoreCase("InPatient")) {
                    System.out.print("Enter daily room charge: ");
                    double roomCharge = scanner.nextDouble();
                    System.out.print("Enter number of days: ");
                    int daysAdmitted = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    patients.add(new InPatient(patientId, name, age, diagnosis, roomCharge, daysAdmitted));
                } else if (patientType.equalsIgnoreCase("OutPatient")) {
                    System.out.print("Enter consultation fee: ");
                    double consultationFee = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline
                    patients.add(new OutPatient(patientId, name, age, diagnosis, consultationFee));
                } else {
                    System.out.println("Invalid patient type! Skipping...");
                }
            }

            for (Patient patient : patients) {
                patient.getPatientDetails();
                System.out.println("Bill Amount: $" + patient.calculateBill());
                if (patient instanceof MedicalRecord) {
                    MedicalRecord medicalRecord = (MedicalRecord) patient;
                    medicalRecord.addRecord("Diagnosis: " + patient.getDiagnosis());
                    System.out.println(medicalRecord.viewRecords());
                }
            }
        } finally {
            scanner.close();
        }
    }
}

// Abstract class Patient
abstract class Patient {
    private String patientId;
    private String name;
    private int age;
    private String diagnosis;

    public Patient(String patientId, String name, int age, String diagnosis) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.diagnosis = diagnosis;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void getPatientDetails() {
        System.out.println("Patient ID: " + patientId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Diagnosis: " + diagnosis);
    }

    public abstract double calculateBill();
}

// Interface for Medical Records
interface MedicalRecord {
    void addRecord(String record);
    String viewRecords();
}

// InPatient subclass
class InPatient extends Patient implements MedicalRecord {
    private double roomCharge;
    private int daysAdmitted;
    private List<String> medicalRecords = new ArrayList<>();

    public InPatient(String patientId, String name, int age, String diagnosis, double roomCharge, int daysAdmitted) {
        super(patientId, name, age, diagnosis);
        this.roomCharge = roomCharge;
        this.daysAdmitted = daysAdmitted;
    }

    @Override
    public double calculateBill() {
        return roomCharge * daysAdmitted;
    }

    @Override
    public void addRecord(String record) {
        medicalRecords.add(record);
    }

    @Override
    public String viewRecords() {
        return "Medical Records: " + String.join(", ", medicalRecords);
    }
}

// OutPatient subclass
class OutPatient extends Patient implements MedicalRecord {
    private double consultationFee;
    private List<String> medicalRecords = new ArrayList<>();

    public OutPatient(String patientId, String name, int age, String diagnosis, double consultationFee) {
        super(patientId, name, age, diagnosis);
        this.consultationFee = consultationFee;
    }

    @Override
    public double calculateBill() {
        return consultationFee;
    }

    @Override
    public void addRecord(String record) {
        medicalRecords.add(record);
    }

    @Override
    public String viewRecords() {
        return "Medical Records: " + String.join(", ", medicalRecords);
    }
}

/*
Input:
Enter the number of patients: 2
Enter details for patient 1:
Enter patient type (InPatient/OutPatient): InPatient
Enter patient ID: P001
Enter name: Prakul
Enter age: 21
Enter diagnosis: flu
Enter daily room charge: 500
Enter number of days: 5
Enter details for patient 2:
Enter patient type (InPatient/OutPatient): OutPatient
Enter patient ID: P002
Enter name: parth
Enter age: 23
Enter diagnosis: Fever
Enter consultation fee: 200

Output:
Patient ID: P001
Name: prakul
Age: 21
Diagnosis: flu
Bill Amount: $2500.0
Medical Records: Diagnosis: flu
Patient ID: P002
Name: parth
Age: 23
Diagnosis: Fever
Bill Amount: $200.0
Medical Records: Diagnosis: Fever
*/
