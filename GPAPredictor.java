import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class GPAPredictor extends JFrame {
    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JLabel gpaLabel;
    private JButton addButton, resetButton;

    public GPAPredictor() {
        setTitle("GPA Calculator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 249, 250));

        // Create header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(248, 249, 250));
        JLabel titleLabel = new JLabel("GPA Calculator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 150, 243));
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create table
        createTable();

        // Create buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(248, 249, 250));

        addButton = createStyledButton("Add Course", new Color(33, 150, 243));
        resetButton = createStyledButton("Reset", new Color(244, 67, 54));

        addButton.addActionListener(e -> addNewCourse());
        resetButton.addActionListener(e -> resetCalculator());

        buttonPanel.add(addButton);
        buttonPanel.add(resetButton);

        // Create GPA label
        gpaLabel = new JLabel("GPA: 0.00");
        gpaLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gpaLabel.setForeground(new Color(33, 150, 243));
        gpaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanel.add(gpaLabel);

        mainPanel.add(new JScrollPane(courseTable), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void createTable() {
        String[] columns = {"Course Name", "Credits", "Marks", "Grade"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 3; // Grade column is not editable
            }
        };

        courseTable = new JTable(tableModel);
        courseTable.setRowHeight(35);
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseTable.setShowGrid(true);
        courseTable.setGridColor(new Color(224, 224, 224));
        courseTable.setSelectionBackground(new Color(187, 222, 251));
        courseTable.setSelectionForeground(Color.BLACK);

        // Custom header
        JTableHeader header = courseTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(33, 150, 243));
        header.setForeground(Color.WHITE);

        // Custom cell renderer
        courseTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                return c;
            }
        });

        // Add table model listener for automatic updates
        tableModel.addTableModelListener(e -> {
            if (e.getColumn() == 2) { // Marks column
                int row = e.getFirstRow();
                try {
                    String marksStr = (String) tableModel.getValueAt(row, 2);
                    if (marksStr != null && !marksStr.isEmpty()) {
                        double marks = Double.parseDouble(marksStr);
                        if (marks < 0 || marks > 100) {
                            JOptionPane.showMessageDialog(this,
                                "Marks must be between 0 and 100",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE);
                            tableModel.setValueAt("", row, 2);
                            return;
                        }
                        String grade = calculateGrade(marks);
                        tableModel.setValueAt(grade, row, 3);
                        updateGPA();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for marks",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                    tableModel.setValueAt("", row, 2);
                }
            }
        });
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

    private void addNewCourse() {
        tableModel.addRow(new Object[]{"", "", "", ""});
    }

    private void resetCalculator() {
        tableModel.setRowCount(0);
        gpaLabel.setText("GPA: 0.00");
    }

    private String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        if (marks >= 85) return "A";
        if (marks >= 80) return "A-";
        if (marks >= 75) return "B+";
        if (marks >= 70) return "B";
        if (marks >= 65) return "B-";
        if (marks >= 60) return "C+";
        if (marks >= 55) return "C";
        if (marks >= 50) return "C-";
        if (marks >= 45) return "D+";
        if (marks >= 40) return "D";
        return "F";
    }

    private double calculateGradePoints(String grade) {
        switch (grade) {
            case "A+": return 4.0;
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            default: return 0.0;
        }
    }

    private void updateGPA() {
        double totalGradePoints = 0;
        double totalCredits = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String creditsStr = (String) tableModel.getValueAt(i, 1);
            String grade = (String) tableModel.getValueAt(i, 3);

            if (creditsStr != null && !creditsStr.isEmpty() && grade != null && !grade.isEmpty()) {
                try {
                    double credits = Double.parseDouble(creditsStr);
                    double gradePoints = calculateGradePoints(grade);
                    totalGradePoints += credits * gradePoints;
                    totalCredits += credits;
                } catch (NumberFormatException e) {
                    // Skip invalid credit values
                }
            }
        }

        if (totalCredits > 0) {
            double gpa = totalGradePoints / totalCredits;
            gpaLabel.setText(String.format("GPA: %.2f", gpa));
        } else {
            gpaLabel.setText("GPA: 0.00");
        }
    }
}