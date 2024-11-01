package gui;

import sevices.TimetableGenerator;
import sevices.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import models.Course;
import models.Room;
import java.sql.*;

public class AdminPanel extends JPanel {
    private JButton generateButton;
    private JButton viewButton;

    public AdminPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 255));

        JLabel titleLabel = new JLabel("Admin Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 37, 41));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 0));
        buttonPanel.setBackground(new Color(250, 250, 255));

        generateButton = new JButton("Generate Timetable");
        generateButton.setFont(new Font("Arial", Font.BOLD, 16));
        generateButton.setBackground(new Color(76, 175, 80));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);

        viewButton = new JButton("View Timetable");
        viewButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewButton.setBackground(new Color(33, 150, 243));
        viewButton.setForeground(Color.WHITE);
        viewButton.setFocusPainted(false);

        buttonPanel.add(generateButton);
        buttonPanel.add(viewButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Generate timetable action
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Load courses, rooms, and availability data
                Map<String, Object> data = loadData();
                TimetableGenerator generator = new TimetableGenerator(
                        (java.util.List<Course>) data.get("courses"),
                        (java.util.List<Room>) data.get("rooms"),
                        (Map<String, java.util.List<String>>) data.get("availability"));

                boolean success = generator.generateTimetable();
                if (success) {
                    JOptionPane.showMessageDialog(null, "Timetable generated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to generate timetable.");
                }
            }
        });

        // View timetable action
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(AdminPanel.this);
                topFrame.setContentPane(new TimetableDisplay());
                topFrame.validate();
            }
        });
    }

    // Load data for timetable generation
    private Map<String, Object> loadData() {
        // This should load courses, rooms, and teacher availability from the database
        DatabaseManager dbManager = DatabaseManager.getInstance();
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("courses", dbManager.getCourses());
            data.put("rooms", dbManager.getRooms());
            data.put("availability", dbManager.getTeacherAvailability());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }
}
