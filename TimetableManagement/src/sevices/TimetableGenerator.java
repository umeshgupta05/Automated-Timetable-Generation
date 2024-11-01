package sevices;

import models.Course;
import models.Room;
import models.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimetableGenerator {
    private List<Course> courses;
    private List<Room> rooms;
    private Map<String, List<String>> teacherAvailability;
    private List<Schedule> timetable;

    public TimetableGenerator(List<Course> courses, List<Room> rooms, Map<String, List<String>> teacherAvailability) {
        this.courses = courses;
        this.rooms = rooms;
        this.teacherAvailability = teacherAvailability;
        this.timetable = new ArrayList<>();
    }

    public boolean generateTimetable() {
        return backtrack(0);
    }

    private boolean backtrack(int courseIndex) {
        if (courseIndex == courses.size()) return true; // All courses are scheduled

        Course course = courses.get(courseIndex);
        for (Room room : rooms) {
            for (String day : List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")) {
                for (String timeslot : List.of("9-10", "10-11", "11-12", "1-2", "2-3")) {
                    if (isAssignmentFeasible(course, room, day, timeslot)) {
                        Schedule schedule = new Schedule(timetable.size() + 1, course.getId(),
                                room.getId(), day, timeslot, course.getCode());
                        timetable.add(schedule);

                        if (backtrack(courseIndex + 1)) return true;

                        timetable.remove(timetable.size() - 1); // Backtrack
                    }
                }
            }
        }
        return false;
    }

    private boolean isAssignmentFeasible(Course course, Room room, String day, String timeslot) {
        if (course.getCapacity() > room.getCapacity()) return false; // Room capacity check
        if (course.requiresLab() && !room.hasLabEquipment()) return false; // Lab requirement

        for (Schedule schedule : timetable) {
            if (schedule.getRoomId() == room.getId() && schedule.getDay().equals(day)
                    && schedule.getTimeslot().equals(timeslot)) {
                return false; // Room already occupied
            }
        }
        return true;
    }

    public List<Schedule> getTimetable() {
        return timetable;
    }
}
