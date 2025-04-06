import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Student Assistant");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 249, 250));

        // Create header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(248, 249, 250));
        JLabel titleLabel = new JLabel("Student Assistant");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 150, 243));
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonsPanel.setBackground(new Color(248, 249, 250));

        JButton gpaButton = createStyledButton("GPA Calculator", new Color(33, 150, 243));
        JButton timetableButton = createStyledButton("Timetable Manager", new Color(33, 150, 243));
        JButton mentalHealthButton = createStyledButton("Mental Health Support", new Color(33, 150, 243));

        gpaButton.addActionListener(e -> {
            GPAPredictor gpaPredictor = new GPAPredictor();
            gpaPredictor.setVisible(true);
        });

        timetableButton.addActionListener(e -> {
            TimeTableNotifier timeTableNotifier = new TimeTableNotifier();
            timeTableNotifier.setVisible(true);
        });

        mentalHealthButton.addActionListener(e -> {
            JFrame mentalHealthFrame = new JFrame("Mental Health Support");
            mentalHealthFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mentalHealthFrame.setSize(800, 600);
            mentalHealthFrame.setLocationRelativeTo(null);
            mentalHealthFrame.add(new MentalHealthSupport());
            mentalHealthFrame.setVisible(true);
        });

        buttonsPanel.add(gpaButton);
        buttonsPanel.add(timetableButton);
        buttonsPanel.add(mentalHealthButton);

        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(15, 30, 15, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }
}