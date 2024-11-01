package gui;

import models.Schedule;
import sevices.DatabaseManager;
import sevices.TimetableGenerator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import models.Course;
import models.Room;

public class TimetableDisplay extends JPanel {
    private JTable timetableTable;

    public TimetableDisplay() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        JLabel titleLabel = new JLabel("Generated Timetable", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 37, 41));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Course", "Room", "Day", "Timeslot", "Teacher"};
        timetableTable = new JTable();
        timetableTable.setModel(new DefaultTableModel(columnNames, 0));
        timetableTable.setRowHeight(25);
        timetableTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        timetableTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(timetableTable);
        add(scrollPane, BorderLayout.CENTER);

        loadTimetable();
    }

    private void loadTimetable() {
        DefaultTableModel model = (DefaultTableModel) timetableTable.getModel();
        model.setRowCount(0); // Clear existing rows

        try {
            // Fetch data from the database
            DatabaseManager dbManager = DatabaseManager.getInstance();
            List<Course> courses = dbManager.getCourses();
            List<Room> rooms = dbManager.getRooms();
            Map<String, List<String>> teacherAvailability = dbManager.getTeacherAvailability();

            // Pass data to the TimetableGenerator
            TimetableGenerator generator = new TimetableGenerator(courses, rooms, teacherAvailability);
            generator.generateTimetable(); // Call to generate timetable

            // Populate the table with timetable data
            for (Schedule schedule : generator.getTimetable()) { // Call getTimetable
                model.addRow(new Object[]{
                        schedule.getCourseId(),
                        schedule.getRoomId(),
                        schedule.getDay(),
                        schedule.getTimeslot(),
                        schedule.getTeacherId()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading timetable: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
