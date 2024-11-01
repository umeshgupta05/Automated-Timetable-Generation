package models;

public class Schedule {
    private int id;
    private int courseId;
    private int roomId;
    private String day;
    private String timeslot;
    private String teacherId;

    public Schedule(int id, int courseId, int roomId, String day, String timeslot, String teacherId) {
        this.id = id;
        this.courseId = courseId;
        this.roomId = roomId;
        this.day = day;
        this.timeslot = timeslot;
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getDay() {
        return day;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public String getTeacherId() {
        return teacherId;
    }
}
