import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class TimeTableNotifier extends JFrame {
    private JTable timetableTable;
    private DefaultTableModel tableModel;
    private JButton addButton, removeButton;

    public TimeTableNotifier() {
        setTitle("Timetable Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 249, 250));

        // Create header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(248, 249, 250));
        JLabel titleLabel = new JLabel("Timetable Manager");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(76, 175, 80));
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create table
        createTable();

        // Create buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(248, 249, 250));

        addButton = createStyledButton("Add Class", new Color(76, 175, 80));
        removeButton = createStyledButton("Remove Class", new Color(244, 67, 54));

        addButton.addActionListener(e -> addNewClass());
        removeButton.addActionListener(e -> removeSelectedClass());

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        mainPanel.add(new JScrollPane(timetableTable), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void createTable() {
        String[] columns = {"Day", "Time", "Course", "Room", "Instructor"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        timetableTable = new JTable(tableModel);
        timetableTable.setRowHeight(35);
        timetableTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        timetableTable.setShowGrid(true);
        timetableTable.setGridColor(new Color(224, 224, 224));
        timetableTable.setSelectionBackground(new Color(187, 222, 251));
        timetableTable.setSelectionForeground(Color.BLACK);

        // Custom header
        JTableHeader header = timetableTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(76, 175, 80));
        header.setForeground(Color.WHITE);

        // Custom cell renderer
        timetableTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

    private void addNewClass() {
        tableModel.addRow(new Object[]{"", "", "", "", ""});
    }

    private void removeSelectedClass() {
        int selectedRow = timetableTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this,
                "Please select a class to remove",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}