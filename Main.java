import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private String rollNumber;
    private String grade;
    private int age; // Additional attribute

    public Student(String name, String rollNumber, String grade, int age) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.age = age;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade + ", Age: " + age;
    }
}

class StudentManagementSystem {
    private ArrayList<Student> students;
    private String filename = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile();
    }

    public void addStudent(Student student) {
        // Validation: Check if roll number is unique and not empty
        if (student.getName().trim().isEmpty() || student.getRollNumber().trim().isEmpty()) {
            System.out.println("Error: Name and Roll Number cannot be empty.");
            return;
        }
        for (Student s : students) {
            if (s.getRollNumber().equals(student.getRollNumber())) {
                System.out.println("Error: Roll Number already exists.");
                return;
            }
        }
        students.add(student);
        saveToFile();
        System.out.println("Student added successfully.");
    }

    public void removeStudent(String rollNumber) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            students.remove(student);
            saveToFile();
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public Student searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equals(rollNumber)) {
                return s;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    public void editStudent(String rollNumber, Scanner scanner) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            System.out.print("Enter new name (current: " + student.getName() + "): ");
            String name = scanner.nextLine();
            if (!name.trim().isEmpty()) {
                student.setName(name);
            }

            System.out.print("Enter new grade (current: " + student.getGrade() + "): ");
            String grade = scanner.nextLine();
            if (!grade.trim().isEmpty()) {
                student.setGrade(grade);
            }

            System.out.print("Enter new age (current: " + student.getAge() + "): ");
            String ageStr = scanner.nextLine();
            if (!ageStr.trim().isEmpty()) {
                try {
                    int age = Integer.parseInt(ageStr);
                    if (age > 0) {
                        student.setAge(age);
                    } else {
                        System.out.println("Invalid age. Keeping current.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format. Keeping current.");
                }
            }

            saveToFile();
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : students) {
                writer.write(s.getName() + "," + s.getRollNumber() + "," + s.getGrade() + "," + s.getAge());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String rollNumber = parts[1];
                    String grade = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    students.add(new Student(name, rollNumber, grade, age));
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's fine
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Remove Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter roll number: ");
                    String roll = scanner.nextLine();
                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    sms.addStudent(new Student(name, roll, grade, age));
                    break;
                case 2:
                    System.out.print("Enter roll number to edit: ");
                    String editRoll = scanner.nextLine();
                    sms.editStudent(editRoll, scanner);
                    break;
                case 3:
                    System.out.print("Enter roll number to remove: ");
                    String removeRoll = scanner.nextLine();
                    sms.removeStudent(removeRoll);
                    break;
                case 4:
                    System.out.print("Enter roll number to search: ");
                    String searchRoll = scanner.nextLine();
                    Student found = sms.searchStudent(searchRoll);
                    if (found != null) {
                        System.out.println(found);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 5:
                    sms.displayAllStudents();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}