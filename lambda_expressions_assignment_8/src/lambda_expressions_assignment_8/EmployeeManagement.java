package lambda_expressions_assignment_8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Employee {
    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }
}

@FunctionalInterface
interface SalaryCalculator {
    double calculateSalary(Employee employee);
}

public class EmployeeManagement {
    public static void main(String[] args) {
        // Create a list of Employee objects
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(101, "John Doe", 60000.0));
        employees.add(new Employee(102, "Jane Smith", 48000.0));
        employees.add(new Employee(103, "Bob Johnson", 55000.0));
        employees.add(new Employee(104, "Alice Brown", 75000.0));

        // Lambda expressions to calculate salary, bonus, and total compensation
        SalaryCalculator annualSalaryCalculator = employee -> employee.getSalary() * 12;
        SalaryCalculator bonusCalculator = employee -> annualSalaryCalculator.calculateSalary(employee) * 0.10;
        SalaryCalculator totalCompensationCalculator = employee -> annualSalaryCalculator.calculateSalary(employee) + bonusCalculator.calculateSalary(employee);

        // Filter employees with a salary less than $50,000
        List<Employee> filteredEmployees = employees.stream()
                .filter(employee -> annualSalaryCalculator.calculateSalary(employee) < 50000)
                .sorted(Comparator.comparing(Employee::getName))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        // Print names and total compensation of sorted employees
        System.out.println("Employees with salary less than $50,000:");
        filteredEmployees.forEach(employee -> {
            double totalCompensation = totalCompensationCalculator.calculateSalary(employee);
            System.out.println("Name: " + employee.getName() + ", Total Compensation: $" + totalCompensation);
        });

        // Constructor reference to create an employee object
        Employee newEmployee = createEmployee(101, "John Doe", 60000.0);

        // Find the average salary of all employees
        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
        System.out.println("Average Salary: $" + averageSalary);

        // Find the employee with the highest salary
        Employee highestPaidEmployee = employees.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);
        if (highestPaidEmployee != null) {
            System.out.println("Employee with the highest salary: " + highestPaidEmployee.getName() + ", Salary: $" + highestPaidEmployee.getSalary());
        }
    }

    // Constructor reference to create an employee object
    public static Employee createEmployee(int id, String name, double salary) {
        return new Employee(id, name, salary);
    }
}

