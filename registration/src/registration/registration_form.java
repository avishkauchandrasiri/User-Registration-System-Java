package registration;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class registration_form extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final JLabel userreg = new JLabel("User Registration Form");
    private JTextField FirstNme;
    private JTextField LastName;
    private JTextField ContactNo;
    private JTextField mail;
    private JTextField DateofBirth;
    private JTextField nicField;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/userregistration";
    private static final String DB_USER = "root"; // Change if different
    private static final String DB_PASSWORD = ""; // Add your MySQL password

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    registration_form frame = new registration_form();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public registration_form() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel fn = new JLabel("First Name :");
        fn.setFont(new Font("Tahoma", Font.BOLD, 10));
        fn.setBounds(46, 54, 69, 13);
        contentPane.add(fn);
        userreg.setHorizontalAlignment(SwingConstants.CENTER);
        userreg.setVerticalTextPosition(SwingConstants.TOP);
        userreg.setBounds(123, 10, 187, 20);
        userreg.setForeground(new Color(128, 0, 255));
        userreg.setFont(new Font("Tahoma", Font.BOLD, 16));
        userreg.setVerticalAlignment(SwingConstants.TOP);
        contentPane.add(userreg);
        
        JLabel ln = new JLabel("Last Name :");
        ln.setFont(new Font("Tahoma", Font.BOLD, 10));
        ln.setBounds(46, 92, 69, 13);
        contentPane.add(ln);
        
        JLabel ConNo = new JLabel("Contact No :");
        ConNo.setFont(new Font("Tahoma", Font.BOLD, 10));
        ConNo.setBounds(46, 130, 69, 12);
        contentPane.add(ConNo);
        
        JLabel Email = new JLabel("Email :");
        Email.setFont(new Font("Tahoma", Font.BOLD, 10));
        Email.setBounds(230, 54, 44, 12);
        contentPane.add(Email);
        
        JLabel dob = new JLabel("Date of Birth :");
        dob.setFont(new Font("Tahoma", Font.BOLD, 10));
        dob.setBounds(230, 92, 78, 12);
        contentPane.add(dob);
        
        JLabel nic = new JLabel("NIC :");
        nic.setFont(new Font("Tahoma", Font.BOLD, 10));
        nic.setBounds(230, 130, 44, 12);
        contentPane.add(nic);
        
        FirstNme = new JTextField();
        FirstNme.setBounds(111, 51, 96, 18);
        contentPane.add(FirstNme);
        FirstNme.setColumns(10);
        
        LastName = new JTextField();
        LastName.setBounds(111, 89, 96, 18);
        contentPane.add(LastName);
        LastName.setColumns(10);
        
        ContactNo = new JTextField();
        ContactNo.setBounds(111, 127, 96, 18);
        contentPane.add(ContactNo);
        ContactNo.setColumns(10);
        
        mail = new JTextField();
        mail.setBounds(277, 51, 127, 18);
        contentPane.add(mail);
        mail.setColumns(10);
        
        DateofBirth = new JTextField();
        DateofBirth.setBounds(312, 89, 92, 18);
        contentPane.add(DateofBirth);
        DateofBirth.setColumns(10);
        
        nicField = new JTextField();
        nicField.setBounds(277, 127, 127, 18);
        contentPane.add(nicField);
        nicField.setColumns(10);
        
        JButton RegButton = new JButton("Register");
        RegButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        RegButton.setForeground(Color.BLACK);
        RegButton.setBackground(new Color(255, 128, 255));
        RegButton.setFont(new Font("Tahoma", Font.BOLD, 10));
        RegButton.setBounds(157, 190, 84, 20);
        contentPane.add(RegButton);
    }

    /**
     * Method to register user in the database
     */
    private void registerUser() {
        // Get data from text fields
        String firstName = FirstNme.getText().trim();
        String lastName = LastName.getText().trim();
        String contactNo = ContactNo.getText().trim();
        String email = mail.getText().trim();
        String dateOfBirth = DateofBirth.getText().trim();
        String nic = nicField.getText().trim();

        // Validate input fields
        if (firstName.isEmpty() || lastName.isEmpty() || contactNo.isEmpty() || 
            email.isEmpty() || dateOfBirth.isEmpty() || nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // SQL insert query
            String sql = "INSERT INTO registerinformation (FirstName, LastName, ContactNo, Email, DateofBirth, NIC) VALUES (?, ?, ?, ?, ?, ?)";
            
            // Create prepared statement
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, contactNo);
            pstmt.setString(4, email);
            pstmt.setString(5, dateOfBirth); // In real application, convert to Date object
            pstmt.setString(6, nic);
            
            // Execute insert
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register user!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "MySQL JDBC Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to clear the form after successful registration
     */
    private void clearForm() {
        FirstNme.setText("");
        LastName.setText("");
        ContactNo.setText("");
        mail.setText("");
        DateofBirth.setText("");
        nicField.setText("");
    }
}
