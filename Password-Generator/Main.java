import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new C();
            }
        });
    }
}

class C extends JFrame {
    private JButton pass_gen, pass_manager;
    
    C() {
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        pass_gen = new JButton("Generate Password");
        pass_manager = new JButton("Password Manager");
        
        pass_gen.setAlignmentX(Component.CENTER_ALIGNMENT);
        pass_manager.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonPanel.add(Box.createVerticalStrut(100));
        buttonPanel.add(pass_gen);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(pass_manager);
        
        add(buttonPanel, BorderLayout.CENTER);
        
        setSize(500, 400);
        setTitle("Password Manager");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pass_gen.addActionListener(e -> {
            pass_gen interfaceWindow = new pass_gen();
            interfaceWindow.setVisible(true);
        });
        
        pass_manager.addActionListener(e -> {
            manag managerWindow = new manag();
            managerWindow.setVisible(true);
        });
    }
}
