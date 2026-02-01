import java.io.*;
import java.util.*;

public class MainApp {
    private static final String TEXT_FILE = "employees.txt";
    private static final String BINARY_FILE = "employees.ser";

    public static void main(String[] args) {
        System.out.println("=== File Handling & Serialization Demo ===\n");
        List<Employee> employees = readEmployeesFromTextFile();
        serializeEmployees(employees);
        List<Employee> deserializedEmployees = deserializeEmployees();

        displayEmployees(deserializedEmployees);

        System.out.println("\n=== Demo Complete ===");
    }

    public static List<Employee> readEmployeesFromTextFile() {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TEXT_FILE))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // Skip empty/comment lines

                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                        String name = parts[0].trim();
                        int id = Integer.parseInt(parts[1].trim());
                        double salary = Double.parseDouble(parts[2].trim());

                        Date hireDate = new Date(parts[3].trim());

                        Employee emp = new Employee(name, id, salary, hireDate);
                        employees.add(emp);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing line " + lineNum + ": " + line);
                    }
                } else {
                    System.err.println("Invalid format at line " + lineNum + ": " + line);
                }
            }
            System.out.println("✅ Read " + employees.size() + " employees from " + TEXT_FILE);
        } catch (FileNotFoundException e) {
            System.err.println("❌ Text file not found: " + TEXT_FILE);
            System.err.println("Create 'employees.txt' with sample data first!");
        } catch (IOException e) {
            System.err.println("❌ Error reading text file: " + e.getMessage());
        }
        return employees;
    }

    public static void serializeEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("⚠️ No employees to serialize");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(BINARY_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(employees);
            System.out.println("✅ Serialized " + employees.size() + " employees to " + BINARY_FILE);
        } catch (IOException e) {
            System.err.println("❌ Serialization error: " + e.getMessage());
        }
    }

    public static List<Employee> deserializeEmployees() {
        List<Employee> employees = new ArrayList<>();

        File file = new File(BINARY_FILE);
        if (!file.exists()) {
            System.out.println("⚠️ Binary file not found: " + BINARY_FILE);
            return employees;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            employees = (List<Employee>) ois.readObject();
            System.out.println("✅ Deserialized " + employees.size() + " employees from " + BINARY_FILE);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Class not found during deserialization: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("❌ Deserialization error: " + e.getMessage());
        }
        return employees;
    }

    public static void displayEmployees(List<Employee> employees) {
        System.out.println("\n📋 === DESERIALIZED EMPLOYEE LIST ===");
        if (employees.isEmpty()) {
            System.out.println("No employees to display");
            return;
        }

        System.out.println(String.format("%-4s %-15s %-10s %-20s", "ID", "Name", "Salary", "Hire Date"));
        System.out.println("----------------------------------------------------------");
        for (Employee emp : employees) {
            System.out.println(String.format("%-4d %-15s %-10.2f %-20s",
                    emp.getId(), emp.getName(), emp.getSalary(), emp.getHireDate()));
        }
    }
}
