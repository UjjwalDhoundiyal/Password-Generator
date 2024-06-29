import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class pass_manager {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new manag());
    }
}

class manag extends JFrame {
    private JTextField passkey, app_name;
    private JTextArea pass_area, notes_area;
    private JButton pass, app, save, back;
    private Connection conn;
    private String encpassword;

    manag() {
        setLayout(new BorderLayout(10, 10));

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pass_manager", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to database.");
            System.exit(1);
        }

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // App name section
        JPanel appname = new JPanel();
        appname.add(new JLabel("Enter the App Name:"));
        app_name = new JTextField(20);
        appname.add(app_name);
        app = new JButton("Check App");
        appname.add(app);
        topPanel.add(appname);

        // Passphrase section
        JPanel passphrase = new JPanel();
        passphrase.add(new JLabel("Enter the Passphrase:"));
        passkey = new JTextField(20);
        passphrase.add(passkey);
        pass = new JButton("Passphrase");
        passphrase.add(pass);
        topPanel.add(passphrase);

        add(topPanel, BorderLayout.NORTH);

        // Create center panel for password and notes area
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Password and Notes section
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Password:"));
        pass_area = new JTextArea(2, 20);
        pass_area.setEditable(false);
        passwordPanel.add(new JScrollPane(pass_area));
        notes_area = new JTextArea(3, 30);
        passwordPanel.add(new JScrollPane(notes_area));
        centerPanel.add(passwordPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Create bottom panel for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        save = new JButton("Save");
        back = new JButton("Back");
        bottomPanel.add(save);
        bottomPanel.add(back);

        add(bottomPanel, BorderLayout.SOUTH);

        app.addActionListener(e -> checkApp());
        pass.addActionListener(e -> decryptPassword());
        save.addActionListener(e -> saveNotes());
        back.addActionListener(e -> dispose());

        setSize(500, 400);
        setTitle("Manage Passwords");
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void checkApp() {
        String appName = app_name.getText();

        if (appName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "App name cannot be empty.");
            return;
        }

        try {
            String sql = "SELECT password, Notes FROM apps WHERE app_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, appName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	encpassword = rs.getString("password");
                notes_area.setText(rs.getString("Notes"));
                JOptionPane.showMessageDialog(this, "App found!");
            } else {
                JOptionPane.showMessageDialog(this, "App not found.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void decryptPassword() {
        String passphrase = passkey.getText();

        if (encpassword == null || passphrase.isEmpty()) {
            JOptionPane.showMessageDialog(this, "App name or passphrase cannot be empty.");
            return;
        }

        String decryptedPassword = XOREncryption.xorEncryptDecrypt(encpassword, passphrase);
        pass_area.setText(decryptedPassword);
    }

    private void saveNotes() {
        String appName = app_name.getText();
        String notes = notes_area.getText();

        if (appName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "App name cannot be empty.");
            return;
        }

        try {
            String sql = "UPDATE apps SET Notes = ? WHERE app_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, notes);
            stmt.setString(2, appName);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Notes saved successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save notes.");
            }

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

class XOREncryption {
    public static String xorEncryptDecrypt(String input, String key) {
        char[] inputChars = input.toCharArray();
        char[] keyChars = key.toCharArray();
        char[] result = new char[inputChars.length];

        for (int i = 0; i < inputChars.length; i++) {
            result[i] = (char) (inputChars[i] ^ keyChars[i % keyChars.length]);
        }

        return new String(result);
    }
}
