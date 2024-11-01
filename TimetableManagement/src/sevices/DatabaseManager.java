package sevices;

import models.Course;
import models.Room;
import models.Schedule;
import utils.OracleConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        connection = OracleConnector.getConnection();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    // Fetch courses from the database
    public List<Course> getCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM Course";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(rs.getInt("id"), rs.getString("name"),
                        rs.getString("code"), rs.getInt("capacity"), rs.getBoolean("requiresLab"));
                courses.add(course);
            }
        }
        return courses;
    }

    // Fetch rooms from the database
    public List<Room> getRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Room";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Room room = new Room(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("capacity"), rs.getBoolean("hasLabEquipment"));
                rooms.add(room);
            }
        }
        return rooms;
    }

    // Fetch teacher availability
    public Map<String, List<String>> getTeacherAvailability() {
        Map<String, List<String>> teacherAvailability = new HashMap<>();
        String query = "SELECT teacher_id, day, timeslot FROM TeacherAvailability";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String teacherId = rs.getString("teacher_id");
                String day = rs.getString("day");
                String timeslot = rs.getString("timeslot");

                // Initialize list if it doesn't exist
                teacherAvailability.putIfAbsent(teacherId, new ArrayList<>());

                // Add timeslot for the specific teacher
                teacherAvailability.get(teacherId).add(day + " " + timeslot);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherAvailability;
    }

    // Close connection
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
