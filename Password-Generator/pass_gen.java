import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class pass_gen extends JFrame {
    private JButton generate, save, back;
    private JTextField totalLength, alphaLength, digitLength, symbolLength, app_name, phrase;
    private JTextArea passwordArea, notes;
    private Connection conn;

    public pass_gen() {
        setTitle("Password Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setResizable(false);
        setLayout(new BorderLayout());

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pass_manager", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to database.");
            System.exit(1);
        }

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel();
        row1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row1.add(new JLabel("Total Length:"));
        totalLength = new JTextField(20);
        row1.add(totalLength);
        inputPanel.add(row1);

        JPanel row2 = new JPanel();
        row2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row2.add(new JLabel("Alphabets:"));
        alphaLength = new JTextField(20);
        row2.add(alphaLength);
        inputPanel.add(row2);

        JPanel row3 = new JPanel();
        row3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row3.add(new JLabel("Digits:"));
        digitLength = new JTextField(20);
        row3.add(digitLength);
        inputPanel.add(row3);

        JPanel row4 = new JPanel();
        row4.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row4.add(new JLabel("Symbols:"));
        symbolLength = new JTextField(20);
        row4.add(symbolLength);
        inputPanel.add(row4);

        JPanel appNamePanel = new JPanel();
        appNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        appNamePanel.add(new JLabel("App Name:"));
        app_name = new JTextField(20);
        appNamePanel.add(app_name);
        inputPanel.add(appNamePanel);
        
        JPanel phrasePanel = new JPanel();
        phrasePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        phrasePanel.add(new JLabel("Passphrase:"));
        phrase = new JTextField(20);
        phrasePanel.add(phrase);
        inputPanel.add(phrasePanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        generate = new JButton("Generate Password");
        buttonPanel.add(generate);

        save = new JButton("Save");
        buttonPanel.add(save);
        
        back = new JButton("Back");
        buttonPanel.add(back);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());

        passwordArea = new JTextArea(2, 30);
        passwordArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(passwordArea);
        textPanel.add(scrollPane, BorderLayout.CENTER);

        notes = new JTextArea(6, 30);
        JScrollPane notesScrollPane = new JScrollPane(notes);
        textPanel.add(notesScrollPane, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        generate.addActionListener(e -> generatePassword());
        save.addActionListener(e -> savePassword());
        back.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    private void generatePassword() {
        int total_length, digit_length, alpha_length, symbol_length;

        try {
            total_length = Integer.parseInt(totalLength.getText());
            alpha_length = Integer.parseInt(alphaLength.getText());
            digit_length = Integer.parseInt(digitLength.getText());
            symbol_length = Integer.parseInt(symbolLength.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
            return;
        }

        int length_checker = alpha_length + digit_length + symbol_length;

        if (length_checker > total_length) {
            JOptionPane.showMessageDialog(this, "The total length exceeds the specified length. Please recheck.");
            return;
        }

        List<Character> password = new ArrayList<>();

        int[][] symbolRange = {{33, 47}, {58, 64}, {91, 96}, {123, 126}};
        int min_digit = 48, max_digit = 57;
        int[][] alphaRange = {{65, 90}, {97, 122}};

        for (int i = 0; i < alpha_length; i++) {
            int[] range = alphaRange[(int) (Math.random() * alphaRange.length)];
            int rand = (int) (Math.random() * (range[1] - range[0] + 1)) + range[0];
            password.add((char) rand);
        }

        for (int i = 0; i < digit_length; i++) {
            int rand = (int) (Math.random() * (max_digit - min_digit + 1)) + min_digit;
            password.add((char) rand);
        }

        for (int i = 0; i < symbol_length; i++) {
            int[] range = symbolRange[(int) (Math.random() * symbolRange.length)];
            int rand = (int) (Math.random() * (range[1] - range[0] + 1)) + range[0];
            password.add((char) rand);
        }

        while (password.size() < total_length) {
            double choice = Math.random();

            if (choice < 0.33) {
                int[] range = alphaRange[(int) (Math.random() * alphaRange.length)];
                int rand = (int) (Math.random() * (range[1] - range[0] + 1)) + range[0];
                password.add((char) rand);
            } else if (choice < 0.66) {
                int rand = (int) (Math.random() * (max_digit - min_digit + 1)) + min_digit;
                password.add((char) rand);
            } else {
                int[] range = symbolRange[(int) (Math.random() * symbolRange.length)];
                int rand = (int) (Math.random() * (range[1] - range[0] + 1)) + range[0];
                password.add((char) rand);
            }
        }

        Collections.shuffle(password);

        StringBuilder passwordStr = new StringBuilder();
        for (char ch : password) {
            passwordStr.append(ch);
        }

        passwordArea.setText(passwordStr.toString());
    }

    private void savePassword() {
        String appName = app_name.getText();
        String password = passwordArea.getText();
        String userNotes = notes.getText();
        String key = phrase.getText();

        String encryptedPassword = XOREncryption.xorEncryptDecrypt(password, key);

        try {
            String sql = "INSERT INTO apps (app_name, password, Notes) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, appName);
            stmt.setString(2, encryptedPassword);
            stmt.setString(3, userNotes);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Password saved successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save password.");
            }

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    /*class XOREncryption {
        public String xorEncryptDecrypt(String input, String key) {
            char[] inputChars = input.toCharArray();
            char[] keyChars = key.toCharArray();
            char[] result = new char[inputChars.length];
    
            for (int i = 0; i < inputChars.length; i++) {
                result[i] = (char) (inputChars[i] ^ keyChars[i % keyChars.length]);
            }
    
            return new String(result);
        }
    }*/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(pass_gen::new);
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
