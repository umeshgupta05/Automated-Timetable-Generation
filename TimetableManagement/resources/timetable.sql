CREATE TABLE Course (
    id INT PRIMARY KEY,
    name VARCHAR2(100),
    code VARCHAR2(20),
    capacity INT
);

CREATE TABLE Room (
    id INT PRIMARY KEY,
    name VARCHAR2(100),
    capacity INT,
    hasLabEquipment BOOLEAN DEFAULT 0
);

CREATE TABLE Schedule (
    id INT PRIMARY KEY,
    courseId INT REFERENCES Course(id),
    roomId INT REFERENCES Room(id),
    day VARCHAR2(10),
    timeslot VARCHAR2(10),
    teacherId VARCHAR2(20)
);

CREATE TABLE TeacherAvailability (
    teacher_id INT,
    day VARCHAR2(10),
    timeslot VARCHAR2(10),
    PRIMARY KEY (teacher_id, day, timeslot)
);
