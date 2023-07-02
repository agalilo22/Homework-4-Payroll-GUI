import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PayrollProgramGUI {
    private JFrame loginFrame;
    private JFrame payrollFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private List<Employee> employeeList;

    public PayrollProgramGUI() {
        employeeList = new ArrayList<>();
        initializeLoginGUI();


    }

    private void initializeLoginGUI() {

        //Log In Method

        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 150);
        loginFrame.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        loginFrame.setVisible(true);
    }

    private void initializePayrollGUI() {

        //Payroll GUI Method

        payrollFrame = new JFrame("Payroll Program");
        payrollFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        payrollFrame.setSize(600, 400);
        payrollFrame.setLayout(new GridLayout(12, 2));

        JButton About = new JButton("About");
        JLabel empNumLabel = new JLabel("Employee Number:");
        JTextField empNumField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        JLabel sssLabel = new JLabel("SSS Number:");
        JTextField sssField = new JTextField();
        JLabel philHealthLabel = new JLabel("PhilHealth Number:");
        JTextField philHealthField = new JTextField();
        JLabel tinLabel = new JLabel("TIN:");
        JTextField tinField = new JTextField();
        JLabel pagibigLabel = new JLabel("Pagibig Number:");
        JTextField pagibigField = new JTextField();
        JLabel hourlySalaryLabel = new JLabel("Hourly Salary:");
        JTextField hourlySalaryField = new JTextField();
        JLabel hoursWorkedLabel = new JLabel("Number of Hours Worked:");
        JTextField hoursWorkedField = new JTextField();
        JButton addButton = new JButton("Add Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton addLeaveButton = new JButton("Add Leave");
        JButton viewEmployeesButton = new JButton("View Employees");
        JButton calculateGrossWageButton = new JButton("Calculate Gross Wage");
        JButton OpenFileButton = new JButton ("Open CSV File");


        payrollFrame.add(OpenFileButton);
        payrollFrame.add(About);
        payrollFrame.add(empNumLabel);
        payrollFrame.add(empNumField);
        payrollFrame.add(lastNameLabel);
        payrollFrame.add(lastNameField);
        payrollFrame.add(firstNameLabel);
        payrollFrame.add(firstNameField);
        payrollFrame.add(sssLabel);
        payrollFrame.add(sssField);
        payrollFrame.add(philHealthLabel);
        payrollFrame.add(philHealthField);
        payrollFrame.add(tinLabel);
        payrollFrame.add(tinField);
        payrollFrame.add(pagibigLabel);
        payrollFrame.add(pagibigField);
        payrollFrame.add(hourlySalaryLabel);
        payrollFrame.add(hourlySalaryField);
        payrollFrame.add(hoursWorkedLabel);
        payrollFrame.add(hoursWorkedField);
        payrollFrame.add(addButton);
        payrollFrame.add(deleteButton);
        payrollFrame.add(viewEmployeesButton);
        payrollFrame.add(calculateGrossWageButton);

        About.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(payrollFrame, "Motor PH Payroll Prototype");
            }
        });

        OpenFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        String filePath = fileChooser.getSelectedFile().getPath();
                        readCSVFile(filePath);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        calculateGrossWageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateGrossWage( hourlySalaryField.getText(), hoursWorkedField.getText());
            }
        });


        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEmployee(empNumField.getText(), lastNameField.getText(), firstNameField.getText(),
                        sssField.getText(), philHealthField.getText(), tinField.getText(), pagibigField.getText());
                clearFields(empNumField, lastNameField, firstNameField, sssField, philHealthField, tinField, pagibigField);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteEmployee(empNumField.getText());
                clearFields(empNumField, lastNameField, firstNameField, sssField, philHealthField, tinField, pagibigField);
            }
        });

        addLeaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addLeave(empNumField.getText());
                clearFields(empNumField, lastNameField, firstNameField, sssField, philHealthField, tinField, pagibigField);
            }
        });

        viewEmployeesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewEmployees();
            }
        });

        payrollFrame.setVisible(true);
    }


    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Perform authentication logic here
        if (isValidLogin(username, password)) {
            loginFrame.setVisible(false);
            initializePayrollGUI();
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Invalid username or password. Please try again.");
        }
    }

    private boolean isValidLogin(String username, String password) {
        // Add your own authentication logic here
        return username.equals("admin") && password.equals("pass");
    }

    private void addEmployee(String empNum, String lastName, String firstName, String sss, String philHealth, String tin, String pagibig) {
        Employee employee = new Employee(empNum, lastName, firstName, sss, philHealth, tin, pagibig);
        employeeList.add(employee);
        JOptionPane.showMessageDialog(payrollFrame, "Employee added successfully.");
    }

    private void deleteEmployee(String empNum) {
        for (Employee employee : employeeList) {
            if (employee.getEmpNum().equals(empNum)) {
                employeeList.remove(employee);
                JOptionPane.showMessageDialog(payrollFrame, "Employee deleted successfully.");
                return;
            }
        }
        JOptionPane.showMessageDialog(payrollFrame, "Employee not found.");
    }

    private void addLeave(String empNum) {
        for (Employee employee : employeeList) {
            if (employee.getEmpNum().equals(empNum)) {
                employee.addLeave();
                JOptionPane.showMessageDialog(payrollFrame, "Leaves added successfully. Sick: "
                        + employee.getSickLeaves() + ", Vacation: "
                        + employee.getVacationLeaves() + ", Emergency: "
                        + employee.getEmergencyLeaves());
                return;
            }
        }
        JOptionPane.showMessageDialog(payrollFrame, "Employee not found.");
    }

    private void viewEmployees() {
        StringBuilder employeesText = new StringBuilder();
        for (Employee employee : employeeList) {
            employeesText.append("Employee Number: ").append(employee.getEmpNum()).append("\n");
            employeesText.append("Last Name: ").append(employee.getLastName()).append("\n");
            employeesText.append("First Name: ").append(employee.getFirstName()).append("\n");
            employeesText.append("SSS Number: ").append(employee.getSss()).append("\n");
            employeesText.append("PhilHealth Number: ").append(employee.getPhilHealth()).append("\n");
            employeesText.append("TIN: ").append(employee.getTin()).append("\n");
            employeesText.append("Pagibig Number: ").append(employee.getPagibig()).append("\n");
            employeesText.append("Pagibig Number: ").append(employee.getPagibig()).append("\n");
            employeesText.append("\n");
        }

        JTextArea employeesTextArea = new JTextArea(employeesText.toString());
        employeesTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(employeesTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(payrollFrame, scrollPane, "Employee List", JOptionPane.PLAIN_MESSAGE);
    }


    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private class Employee {

        // Classes
        private String empNum;
        private String lastName;
        private String firstName;
        private String sss;
        private String philHealth;
        private String tin;
        private String pagibig;

        private String About;
        private int sickLeaves;
        private int vacationLeaves;
        private int emergencyLeaves;

        public Employee(String empNum, String lastName, String firstName, String sss, String philHealth, String tin, String pagibig) {
            this.empNum = empNum;
            this.lastName = lastName;
            this.firstName = firstName;
            this.sss = sss;
            this.philHealth = philHealth;
            this.tin = tin;
            this.pagibig = pagibig;
            this.About = "Motor PH Requirements";
            this.sickLeaves = 5;
            this.vacationLeaves = 10;
            this.emergencyLeaves = 5;
        }

        public String getEmpNum() {
            return empNum;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getSss() {
            return sss;
        }

        public String getPhilHealth() {
            return philHealth;
        }

        public String getTin() {
            return tin;
        }

        public String getPagibig() {
            return pagibig;
        }

        public int getSickLeaves() {
            return sickLeaves;
        }

        public int getVacationLeaves() {
            return vacationLeaves;
        }

        public int getEmergencyLeaves() {
            return emergencyLeaves;
        }

        public void addLeave() {
            sickLeaves++;
            vacationLeaves++;
            emergencyLeaves++;
        }
    }
    private void calculateGrossWage(String hourlySalaryText, String hoursWorkedText) {
        try {
            double hourlySalary = Double.parseDouble(hourlySalaryText);
            double hoursWorked = Double.parseDouble(hoursWorkedText);

            double grossWage = hourlySalary * hoursWorked;

            JOptionPane.showMessageDialog(payrollFrame, "Gross Wage: PHP" + grossWage);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(payrollFrame, "Invalid input. Please enter numeric values.");
        }
    }
    private void readCSVFile(String filePath) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            for (String item : nextLine) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
        reader.close();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PayrollProgramGUI();
            }
        });
    }
}
