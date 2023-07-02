import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayrollProgram {
    private JFrame loginFrame;
    private JFrame payrollFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private List<Employee> employeeList;
    private String csvFilePath;

    public PayrollProgram() {
        employeeList = new ArrayList<>();
        initializeLoginGUI();
    }

    private void initializeLoginGUI() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new GridBagLayout());

        // Username Label and Text Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        // Password Label and Text Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Add components to the login frame
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        loginFrame.add(usernameLabel, constraints);

        constraints.gridx = 1;
        loginFrame.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        loginFrame.add(passwordLabel, constraints);

        constraints.gridx = 1;
        loginFrame.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        loginFrame.add(loginButton, constraints);

        loginFrame.setVisible(true);
    }

    private void initializePayrollGUI() {
        payrollFrame = new JFrame("Payroll Program");
        payrollFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        payrollFrame.setSize(500, 300);
        payrollFrame.setLocationRelativeTo(null);
        payrollFrame.setLayout(new BorderLayout());

        // Add Employee Panel
        JPanel addEmployeePanel = createAddEmployeePanel();
        payrollFrame.add(addEmployeePanel, BorderLayout.NORTH);

        // Employee List Panel
        JPanel employeeListPanel = createEmployeeListPanel();
        payrollFrame.add(employeeListPanel, BorderLayout.CENTER);

        // Menu Bar
        JMenuBar menuBar = createMenuBar();
        payrollFrame.setJMenuBar(menuBar);

        payrollFrame.setVisible(true);
    }

    private JPanel createAddEmployeePanel() {
        JPanel addEmployeePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Employee Number Label and Text Field
        JLabel empNumLabel = new JLabel("Employee Number:");
        JTextField empNumField = new JTextField(10);

        // Last Name Label and Text Field
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(10);

        // First Name Label and Text Field
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(10);

        // SSS Number Label and Text Field
        JLabel sssLabel = new JLabel("SSS Number:");
        JTextField sssField = new JTextField(10);

        // PhilHealth Number Label and Text Field
        JLabel philHealthLabel = new JLabel("PhilHealth Number:");
        JTextField philHealthField = new JTextField(10);

        // TIN Label and Text Field
        JLabel tinLabel = new JLabel("TIN:");
        JTextField tinField = new JTextField(10);

        // Pagibig Number Label and Text Field
        JLabel pagibigLabel = new JLabel("Pagibig Number:");
        JTextField pagibigField = new JTextField(10);

        // Add Employee Button
        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empNum = empNumField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String sss = sssField.getText().trim();
                String philHealth = philHealthField.getText().trim();
                String tin = tinField.getText().trim();
                String pagibig = pagibigField.getText().trim();

                addEmployee(empNum, lastName, firstName, sss, philHealth, tin, pagibig);

                // Clear the input fields after adding an employee
                empNumField.setText("");
                lastNameField.setText("");
                firstNameField.setText("");
                sssField.setText("");
                philHealthField.setText("");
                tinField.setText("");
                pagibigField.setText("");
            }
        });

        // Add components to the add employee panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        addEmployeePanel.add(empNumLabel, constraints);

        constraints.gridx = 1;
        addEmployeePanel.add(empNumField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        addEmployeePanel.add(lastNameLabel, constraints);

        constraints.gridx = 1;
        addEmployeePanel.add(lastNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        addEmployeePanel.add(firstNameLabel, constraints);

        constraints.gridx = 1;
        addEmployeePanel.add(firstNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        addEmployeePanel.add(sssLabel, constraints);

        constraints.gridx = 1;
        addEmployeePanel.add(sssField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        addEmployeePanel.add(philHealthLabel, constraints);

        constraints.gridx = 1;
        addEmployeePanel.add(philHealthField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        addEmployeePanel.add(tinLabel, constraints);

        constraints.gridx = 1;
        addEmployeePanel.add(tinField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        addEmployeePanel.add(pagibigLabel, constraints);

        constraints.gridx = 1;
        addEmployeePanel.add(pagibigField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        addEmployeePanel.add(addEmployeeButton, constraints);

        return addEmployeePanel;
    }

    private JPanel createEmployeeListPanel() {
        JPanel employeeListPanel = new JPanel(new BorderLayout());

        JTextArea employeeTextArea = new JTextArea(10, 40);
        employeeTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(employeeTextArea);
        employeeListPanel.add(scrollPane, BorderLayout.CENTER);

        // View Employees Button
        JButton viewEmployeesButton = new JButton("View Employees");
        viewEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewEmployees(employeeTextArea);
            }
        });

        employeeListPanel.add(viewEmployeesButton, BorderLayout.SOUTH);

        return employeeListPanel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem openMenuItem = new JMenuItem("Open CSV");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseCSVFile();
            }
        });

        fileMenu.add(openMenuItem);

        menuBar.add(fileMenu);

        return menuBar;
    }

    private void chooseCSVFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(payrollFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            csvFilePath = fileChooser.getSelectedFile().getPath();
            loadEmployeesFromCSV();
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (isValidLogin(username, password)) {
            loginFrame.dispose();
            initializePayrollGUI();
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidLogin(String username, String password) {
        // Same code as before
    }

    private void addEmployee(String empNum, String lastName, String firstName, String sss, String philHealth, String tin, String pagibig) {
        // Same code as before
    }

    private void deleteEmployee(String empNum) {
        // Same code as before
    }

    private void addLeave(String empNum) {
        // Same code as before
    }

    private void viewEmployees(JTextArea employeeTextArea) {
        StringBuilder employeesText = new StringBuilder();
        for (Employee employee : employeeList) {
            employeesText.append("Employee Number: ").append(employee.getEmpNum()).append("\n");
            employeesText.append("Last Name: ").append(employee.getLastName()).append("\n");
            employeesText.append("First Name: ").append(employee.getFirstName()).append("\n");
            employeesText.append("SSS Number: ").append(employee.getSss()).append("\n");
            employeesText.append("PhilHealth Number: ").append(employee.getPhilHealth()).append("\n");
            employeesText.append("TIN: ").append(employee.getTin()).append("\n");
            employeesText.append("Pagibig Number: ").append(employee.getPagibig()).append("\n");
            employeesText.append("\n");
        }

        employeeTextArea.setText(employeesText.toString());
    }

    private void saveEmployeesToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            for (Employee employee : employeeList) {
                String[] data = {
                        employee.getEmpNum(),
                        employee.getLastName(),
                        employee.getFirstName(),
                        employee.getSss(),
                        employee.getPhilHealth(),
                        employee.getTin(),
                        employee.getPagibig()
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeesFromCSV() {
        employeeList.clear();

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 7) {
                    String empNum = nextLine[0];
                    String lastName = nextLine[1];
                    String firstName = nextLine[2];
                    String sss = nextLine[3];
                    String philHealth = nextLine[4];
                    String tin = nextLine[5];
                    String pagibig = nextLine[6];
                    Employee employee = new Employee(empNum, lastName, firstName, sss, philHealth, tin, pagibig);
                    employeeList.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PayrollProgram();
            }
        });
    }
}
