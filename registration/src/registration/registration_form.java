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
    
    // මෙන්න මෙතනට අපි Table එකට අදාළ Variables ගෝලීයව (Globally) ප්‍රකාශ කළා
    private javax.swing.JTable table;
    private javax.swing.table.DefaultTableModel tableModel;
    private javax.swing.JScrollPane scrollPane;

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
        // Window එකේ Size එක Table එක පෙනෙන විදිහට ලොකු කරා (උස 480 කරා)
        setBounds(100, 100, 470, 480);
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
        RegButton.setBounds(180, 170, 84, 20); // බොත්තම මැදට කරලා උඩට ගත්තා
        contentPane.add(RegButton);
        
        // 1. ScrollPane එකක් හදනවා Table එක දාන්න
        scrollPane = new javax.swing.JScrollPane();
        scrollPane.setBounds(30, 210, 400, 200); // සයිස් එක ලස්සනට හැදුවා
        contentPane.add(scrollPane);
        
        // 2. Table එකේ තීරු (Columns) නම් කරනවා
        String[] columnNames = {"First Name", "Last Name", "Contact", "Email", "DOB", "NIC"};
        tableModel = new javax.swing.table.DefaultTableModel(columnNames, 0);
        table = new javax.swing.JTable(tableModel);
        scrollPane.setViewportView(table);
        
        // 3. ඇප් එක මුලින්ම ලෝඩ් වෙද්දීම ඩේටාබේස් එකේ තියෙන දත්ත පෙන්වන්න කෝල් කරනවා
        loadUserData();
        
     // 1. Table එකේ Row එකක් ක්ලික් කරද්දී දත්ත ටික උඩ Form එකට ගන්න Event එක
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow(); // ක්ලික් කරපු පේළියේ අංකය ගන්නවා
                
                // ඒ පේළියේ තියෙන දත්ත ටික එකින් එක අරන් Text Fields වලට දානවා
                FirstNme.setText(tableModel.getValueAt(selectedRow, 0).toString());
                LastName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                ContactNo.setText(tableModel.getValueAt(selectedRow, 2).toString());
                mail.setText(tableModel.getValueAt(selectedRow, 3).toString());
                DateofBirth.setText(tableModel.getValueAt(selectedRow, 4).toString());
                nicField.setText(tableModel.getValueAt(selectedRow, 5).toString());
                
                // NIC එක වෙනස් කරන්න දෙන්න බැහැ, මොකද ඒක Primary Key එක වගේනේ
                nicField.setEditable(false); 
            }
        });

        // 2. Update බටන් එක එකතු කිරීම
        JButton UpdateButton = new JButton("Update");
        UpdateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateUser(); // Update කරන්න හදන මෙතඩ් එක
            }

		
        });
        UpdateButton.setForeground(Color.BLACK);
        UpdateButton.setBackground(new Color(128, 255, 128)); // කොළ පාට
        UpdateButton.setFont(new Font("Tahoma", Font.BOLD, 10));
        UpdateButton.setBounds(70, 170, 84, 20); // Register බටන් එකට වම් පැත්තෙන් තියනවා
        contentPane.add(UpdateButton);

        // 3. Delete බටන් එක එකතු කිරීම
        JButton DeleteButton = new JButton("Delete");
        DeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser(); // Delete කරන්න හදන මෙතඩ් එක
            }

			
        });
        DeleteButton.setForeground(Color.BLACK);
        DeleteButton.setBackground(new Color(255, 128, 128)); // රතු පාට
        DeleteButton.setFont(new Font("Tahoma", Font.BOLD, 10));
        DeleteButton.setBounds(290, 170, 84, 20); // Register බටන් එකට දකුණු පැත්තෙන් තියනවා
        contentPane.add(DeleteButton);
    }

    /**
     * Method to register user in the database (Create Operation)
     */
    private void registerUser() {
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String sql = "INSERT INTO registerinformation (FirstName, LastName, ContactNo, Email, DateofBirth, NIC) VALUES (?, ?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, contactNo);
            pstmt.setString(4, email);
            pstmt.setString(5, dateOfBirth); 
            pstmt.setString(6, nic);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                // 4. අලුත් කෙනෙක් හැදුවට පස්සේ Table එක Auto Refresh වෙන්න මෙතන කෝල් කරා
                loadUserData(); 
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
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ඩේටාබේස් එකෙන් දත්ත කියවලා Table එකට දාන කොටස (Read Operation)
     */
    private void loadUserData() {
        tableModel.setRowCount(0); // පරණ දත්ත අයින් කරනවා

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // දත්ත ලබාගන්නා SELECT Query එක
            String sql = "SELECT FirstName, LastName, ContactNo, Email, DateofBirth, NIC FROM registerinformation";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String contactNo = rs.getString("ContactNo");
                String email = rs.getString("Email");
                String dob = rs.getString("DateofBirth");
                String nic = rs.getString("NIC");
                
                Object[] rowData = {firstName, lastName, contactNo, email, dob, nic};
                tableModel.addRow(rowData); // Table එකට දත්ත පේළියක් එකතු කරනවා
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
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
        nicField.setEditable(true); 
    }
    
    /**
     * ඩේටාබේස් එකේ තියෙන දත්ත වෙනස් කිරීම (Update Operation)
     */
    private void updateUser() {
        String firstName = FirstNme.getText().trim();
        String lastName = LastName.getText().trim();
        String contactNo = ContactNo.getText().trim();
        String email = mail.getText().trim();
        String dateOfBirth = DateofBirth.getText().trim();
        String nic = nicField.getText().trim();

        if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a user from the table to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String sql = "UPDATE registerinformation SET FirstName=?, LastName=?, ContactNo=?, Email=?, DateofBirth=? WHERE NIC=?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, contactNo);
            pstmt.setString(4, email);
            pstmt.setString(5, dateOfBirth);
            pstmt.setString(6, nic);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                nicField.setEditable(true);
                clearForm();
                loadUserData(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * ඩේටාබේස් එකෙන් පරිශීලකයෙක් ඉවත් කිරීම (Delete Operation)
     */
    private void deleteUser() {
        String nic = nicField.getText().trim();

        if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a user from the table to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                
                String sql = "DELETE FROM registerinformation WHERE NIC=?";
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, nic);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    nicField.setEditable(true);
                    clearForm();
                    loadUserData(); 
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try { if (pstmt != null) pstmt.close(); if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    }
