package sample.Controls;

import sample.Connectivity.ServerConnection;

public class Lesson {
    private int id;
    private String groupId;
    private String teacherId;
    private String teacherSurname;
    private String cabinet;

    public String getTeacherSurname() {
        return teacherSurname;
    }

    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = teacherSurname;
    }

    private String date;
    private String time;

    public Lesson(int id, String groupId, String teacherId, String cabinet, String date, String time) {
        this.id = id;
        this.groupId = groupId;
        this.teacherId = teacherId;
        this.cabinet = cabinet;
        this.date = date;
        this.time = time;
    }

    public Lesson(String lesson) {
        String[] vals = lesson.split("\\|");
        if (!vals[0].equals("null")) this.id = Integer.parseInt(vals[0]);
        if (!vals[1].equals("null")) this.groupId = vals[1];
        if (!vals[2].equals("null")) this.teacherId = vals[2];
        if (!vals[3].equals("null")) this.cabinet = vals[3];
        if (!vals[4].equals("null")) this.date = vals[4];
        if (!vals[5].equals("null")) this.time = vals[5];
    }

    public Lesson() {
    }

    @Override
    public String toString() {
        return id +
                "|" + groupId +
                "|" + teacherId +
                "|" + cabinet +
                "|" + date +
                "|" + time + "|";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setGroupIdServer(ServerConnection conn, String groupId) {
        this.groupId = groupId;
        conn.sendString("Lesson|setGroupId|" + id + "|" + groupId);
    }
    public void setTeacherIdServer(ServerConnection conn, String teacherId) {
        this.teacherId = teacherId;
        conn.sendString("Lesson|setTeacherId|" + id + "|" + teacherId);
    }
    public void setCabinetServer(ServerConnection conn, String cabinet) {
        this.cabinet = cabinet;
        conn.sendString("Lesson|setCabinet|" + id + "|" + cabinet);
    }
    public void setDateServer(ServerConnection conn, String date) {
        this.date = date;
        conn.sendString("Lesson|setDate|" + id + "|" + date);
    }
    public void setTimeServer(ServerConnection conn, String time) {
        this.time = time;
        conn.sendString("Lesson|setTime|" + id + "|" + time);
    }

    public void deleteServer(ServerConnection conn) {
        conn.sendString("Lesson|delete|" + id + "|" + cabinet);
    }

}
