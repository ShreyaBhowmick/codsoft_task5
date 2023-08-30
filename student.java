import java.io.*;
import java.util.*;

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private static final String DATA_FILE = "C:/Users/Public/Documents/student_program/students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudentData();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudentData();
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
        saveStudentData();
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    private void loadStudentData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    int rollNumber = Integer.parseInt(parts[1].trim());
                    String grade = parts[2].trim();
                    students.add(new Student(name, rollNumber, grade));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveStudentData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Student student : students) {
                writer.write(student.getName() + ", " + student.getRollNumber() + ", " + student.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDataFile() {
        return DATA_FILE;
    }
}

class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            StudentManagementSystem sms = new StudentManagementSystem();
            
            while (true) {
                System.out.println("1. Add Student");
                System.out.println("2. Remove Student");
                System.out.println("3. Search Student");
                System.out.println("4. Display All Students");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter roll number: ");
                        int rollNumber = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline
                        System.out.print("Enter grade: ");
                        String grade = scanner.nextLine();
                        sms.addStudent(new Student(name, rollNumber, grade));
                        System.out.println("Student added.");
                        break;
                    case 2:
                        System.out.print("Enter roll number to remove: ");
                        int removeRollNumber = scanner.nextInt();
                        sms.removeStudent(removeRollNumber);
                        System.out.println("Student removed.");
                        break;
                    case 3:
                        System.out.print("Enter roll number to search: ");
                        int searchRollNumber = scanner.nextInt();
                        Student searchResult = sms.searchStudent(searchRollNumber);
                        if (searchResult != null) {
                            System.out.println("Student found: " + searchResult);
                        } else {
                            System.out.println("Student not found.");
                        }
                        break;
                    case 4:
                        List<Student> allStudents = sms.getAllStudents();
                        System.out.println("All Students:");
                        for (Student student : allStudents) {
                            System.out.println(student);
                        }
                        break;
                    case 5:
                        System.out.println("Exiting the application.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please select again.");
                }
            }
        } 
        finally {
        scanner.close(); 
    }
}
