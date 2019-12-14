package sample.Controls;

import sample.Connectivity.ServerConnection;

public class Group {
    private int id;
    private String level;
    private String subjectId;

    public Group(int id, String level, String subjectId) {
        this.id = id;
        this.level = level;
        this.subjectId = subjectId;
    }

    public Group(String group) {
        String[] vals = group.split("\\|");
        if (!vals[0].equals("null")) this.id = Integer.parseInt(vals[0]);
        if (!vals[1].equals("null")) this.level = vals[1];
        if (!vals[2].equals("null")) this.subjectId = vals[2];
    }

    public Group() {
    }

    @Override
    public String toString() {
        return id + "|" + level + "|" + subjectId + "|";
    }

    public int getId() {
        return id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setLevelServer(ServerConnection conn, String level) {
        this.level = level;
        conn.sendString("Group|setLevel|" + id + "|" + level);
    }

    public void setSubjectServer(ServerConnection conn, String subject) {
        this.subjectId = subject;
        conn.sendString("Group|setSubject|" + id + "|" + subject);
    }

    public void deleteServer(ServerConnection conn) {
        conn.sendString("Group|delete|" + id + "|" + level);
    }

}
