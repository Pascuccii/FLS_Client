package sample.Controls;

import sample.Connectivity.ServerConnection;

public class TeacherSubject {
    private int id;
    private String teacherId;
    private String subjectId;

    public TeacherSubject(int id, String teacherId, String subjectId) {
        this.id = id;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
    }

    public TeacherSubject(String teacherSubject) {
        String[] vals = teacherSubject.split("\\|");
        if (!vals[0].equals("null")) this.id = Integer.parseInt(vals[0]);
        if (!vals[1].equals("null")) this.teacherId = vals[1];
        if (!vals[2].equals("null")) this.subjectId = vals[2];
    }

    public TeacherSubject() {
    }

    @Override
    public String toString() {
        return id +
                "|" + teacherId +
                "|" + subjectId + "|";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void setTeacherIdServer(ServerConnection conn, String teacherId) {
        this.teacherId = teacherId;
        conn.sendString("TeacherSubject|setTeacherId|" + id + "|" + teacherId);
    }
    public void setSubjectIdServer(ServerConnection conn, String subjectId) {
        this.subjectId = subjectId;
        conn.sendString("TeacherSubject|setSubjectId|" + id + "|" + subjectId);
    }

    public void deleteServer(ServerConnection conn) {
        conn.sendString("TeacherSubject|delete|" + id + "|" + teacherId);
    }

}
