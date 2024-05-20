import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Note {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new B();
            }
        });
    }
}

class B extends JFrame {
    private JButton generate;
    private JTextField totalLength, alphaLength, digitLength, symbolLength;
    private JTextArea passwordArea, notes;

    B() {
        setLayout(new FlowLayout());

        add(new JLabel("Total Length:"));
        totalLength = new JTextField(10);
        add(totalLength);

        add(new JLabel("                Alphabets:"));
        alphaLength = new JTextField(10);
        add(alphaLength);

        add(new JLabel("                Digits:"));
        digitLength = new JTextField(10);
        add(digitLength);

        add(new JLabel("                Symbols:"));
        symbolLength = new JTextField(10);
        add(symbolLength);

        generate = new JButton("Generate Password");
        add(generate);

        passwordArea = new JTextArea(3, 30);
        // passwordArea.setEditable(false);
        add(new JScrollPane(passwordArea));

        add(new JLabel("How do You want to remember this:- "));
        notes = new JTextArea(3, 40);
        add(notes);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setTitle("Password Creator");
        setVisible(true);
        setResizable(false);

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });
    }

    private void generatePassword() {
        int total_length, digit_length, alpha_length, symbol_length;

        try {
            total_length = Integer.parseInt(totalLength.getText());
            alpha_length = Integer.parseInt(alphaLength.getText());
            digit_length = Integer.parseInt(digitLength.getText());
            symbol_length = Integer.parseInt(symbolLength.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
            return;
        }

        int length_checker = alpha_length + digit_length + symbol_length;

        if (length_checker > total_length) {
            JOptionPane.showMessageDialog(this, "The total length exceeds the specified length. Please recheck.");
            return;
        }

        List<Character> password = new ArrayList<>();

        int[][] symbolRange = { { 33, 47 }, { 58, 64 }, { 91, 96 }, { 123, 126 } };
        int min_digit = 48, max_digit = 57;
        int[][] alphaRange = { { 65, 90 }, { 97, 122 } };

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
}
