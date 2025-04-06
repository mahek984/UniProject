import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.Random;

public class MentalHealthSupport extends JPanel {
    private JPanel moodPanel;
    private JPanel chatPanel;
    private JPanel supportPanel;
    private JTextArea chatArea;
    private JTextField chatInput;
    private JLabel moodMessageLabel;
    private final String[] MOODS = {
        "Happy", "Neutral", "Stressed", "Anxious", "Sad", "Tired"
    };
    private final Color[] MOOD_COLORS = {
        new Color(255, 223, 186), // Happy - Warm peach
        new Color(240, 240, 240), // Neutral - Light gray
        new Color(255, 182, 193), // Stressed - Light pink
        new Color(176, 196, 222), // Anxious - Light steel blue
        new Color(230, 230, 250), // Sad - Lavender
        new Color(255, 250, 205)  // Tired - Lemon chiffon
    };
    private final String[] MOOD_MESSAGES = {
        "Great to see you're feeling happy! Keep spreading positivity!",
        "Feeling neutral is perfectly normal. Take a moment to reflect.",
        "It's okay to feel stressed. Try some deep breathing exercises.",
        "Anxiety can be overwhelming. Remember to take things one step at a time.",
        "It's okay to feel sad. You're not alone, and this feeling will pass.",
        "Feeling tired? Make sure to get some rest and take care of yourself."
    };
    private final String[] SUPPORT_CONTACTS = {
        "College Counselor: Dr. Smith - 555-0101",
        "Student Support: Ms. Johnson - 555-0102",
        "Emergency Support: 24/7 Hotline - 555-0100",
        "Health Center: Dr. Williams - 555-0103",
        "Academic Advisor: Mr. Brown - 555-0104"
    };

    public MentalHealthSupport() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(248, 249, 250));

        // Create header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(248, 249, 250));
        JLabel titleLabel = new JLabel("Mental Health Support");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 150, 243));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(248, 249, 250));
        tabbedPane.setForeground(new Color(50, 50, 50));

        // Add tabs
        tabbedPane.addTab("Current Mood", createMoodPanel());
        tabbedPane.addTab("Support Chat", createChatPanel());
        tabbedPane.addTab("Direct Support", createSupportPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createMoodPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Mood message label
        moodMessageLabel = new JLabel("How are you feeling today?");
        moodMessageLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        moodMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moodMessageLabel.setForeground(new Color(33, 150, 243));
        panel.add(moodMessageLabel, BorderLayout.NORTH);

        // Mood buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonsPanel.setBackground(new Color(248, 249, 250));

        for (int i = 0; i < MOODS.length; i++) {
            JButton moodButton = createMoodButton(MOODS[i], MOOD_COLORS[i], i);
            buttonsPanel.add(moodButton);
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);
        return panel;
    }

    private JButton createMoodButton(String mood, Color color, int index) {
        JButton button = new JButton(mood);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            setBackground(MOOD_COLORS[index]);
            moodMessageLabel.setText(MOOD_MESSAGES[index]);
            moodMessageLabel.setForeground(Color.BLACK);
        });

        return button;
    }

    private JPanel createChatPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chatArea.setBackground(Color.WHITE);
        chatArea.setBorder(new LineBorder(new Color(224, 224, 224)));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(new Color(248, 249, 250));

        chatInput = new JTextField();
        chatInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chatInput.setBorder(new LineBorder(new Color(224, 224, 224)));

        JButton sendButton = createStyledButton("Send", new Color(33, 150, 243));
        sendButton.addActionListener(e -> sendMessage());
        chatInput.addActionListener(e -> sendMessage());

        inputPanel.add(chatInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        // Add welcome message
        chatArea.append("Support Assistant: Hello! I'm here to listen and help. How can I support you today?\n");

        return panel;
    }

    private JPanel createSupportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Direct Support Contacts");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(33, 150, 243));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Contacts panel
        JPanel contactsPanel = new JPanel(new GridLayout(SUPPORT_CONTACTS.length, 1, 10, 10));
        contactsPanel.setBackground(new Color(248, 249, 250));

        for (String contact : SUPPORT_CONTACTS) {
            JButton contactButton = createContactButton(contact);
            contactsPanel.add(contactButton);
        }

        panel.add(contactsPanel, BorderLayout.CENTER);

        // Emergency button
        JButton emergencyButton = createStyledButton("Emergency Support", new Color(244, 67, 54));
        emergencyButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(emergencyButton, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createContactButton(String contact) {
        JButton button = new JButton(contact);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(224, 224, 224)));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Contact information copied to clipboard.\n" + contact,
                "Contact Information",
                JOptionPane.INFORMATION_MESSAGE);
        });

        return button;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void sendMessage() {
        String message = chatInput.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append("You: " + message + "\n");
            chatInput.setText("");
            
            // Simple keyword-based responses
            String response = generateResponse(message.toLowerCase());
            chatArea.append("Support Assistant: " + response + "\n");
            
            // Scroll to bottom
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        }
    }

    private String generateResponse(String message) {
        if (message.contains("stress") || message.contains("stressed")) {
            return "It's normal to feel stressed. Try taking deep breaths and breaking tasks into smaller steps.";
        } else if (message.contains("sad") || message.contains("depress")) {
            return "I hear you're feeling down. Remember that it's okay to not be okay, and seeking help is a sign of strength.";
        } else if (message.contains("anxiety") || message.contains("worried")) {
            return "Anxiety can be overwhelming. Let's focus on what we can control and take things one step at a time.";
        } else if (message.contains("help")) {
            return "I'm here to help. Would you like to speak with a counselor? You can find contact information in the Direct Support tab.";
        } else if (message.contains("thank")) {
            return "You're welcome! I'm here to support you.";
        } else if (message.contains("bye") || message.contains("goodbye")) {
            return "Take care! Remember, you're stronger than you think.";
        } else {
            return "I'm here to listen. Would you like to tell me more about how you're feeling?";
        }
    }
}