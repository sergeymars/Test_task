package com.example.university;

import com.example.university.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    @Autowired
    private UniversityService universityService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the University Console Application!");

        while (true) {
            System.out.println("Enter a command (or type 'help' for available commands or 'exit' to quit): ");
            String input = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Exiting application.");
                break;
            } else if ("help".equalsIgnoreCase(input)) {
                displayHelp();
            } else {
                processCommand(input);
            }
        }

        scanner.close();
    }

    private void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("  Who is head of department {department_name}");
        System.out.println("  Show {department_name} statistics");
        System.out.println("  Show {department_name} average salary");
        System.out.println("  Show {department_name} employee count");
        System.out.println("  Show all departments");
        System.out.println("  Show all lectors");
        System.out.println("  Global search by {template}");
        System.out.println("  help - display this help menu");
        System.out.println("  exit - quit the application");
    }

    private void processCommand(String input) {
        if (input.startsWith("Who is head of department")) {
            String departmentName = input.substring("Who is head of department".length()).trim();
            System.out.println(universityService.getHeadOfDepartment(departmentName));
        } else if (input.startsWith("Show")) {
            String[] parts = input.split(" ");
            if (parts.length >= 3 && "statistics".equals(parts[2])) {
                String departmentName = parts[1];
                System.out.println(universityService.getDepartmentStatistics(departmentName));
            } else if (parts.length >= 4 && "average".equals(parts[2]) && "salary".equals(parts[3])) {
                String departmentName = parts[1];
                System.out.println("The average salary of " + departmentName + " is " + universityService.getAverageSalary(departmentName));
            } else if (parts.length >= 3 && "employee".equals(parts[2]) && "count".equals(parts[3])) {
                String departmentName = parts[1];
                System.out.println("Employee count: " + universityService.getEmployeeCount(departmentName));
            } else if ("all".equals(parts[1]) && "departments".equals(parts[2])) {
                System.out.println("Departments: " + universityService.getAllDepartments());
            } else if ("all".equals(parts[1]) && "lectors".equals(parts[2])) {
                System.out.println("Lectors: " + universityService.getAllLectors());
            }
        } else if (input.startsWith("Global search by")) {
            String template = input.substring("Global search by".length()).trim();
            System.out.println("Search results: " + universityService.globalSearch(template));
        } else {
            System.out.println("Unknown command. Please try again.");
        }
    }
}
