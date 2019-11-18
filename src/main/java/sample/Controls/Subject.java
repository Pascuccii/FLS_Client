package sample.Controls;

import sample.Connectivity.ServerConnection;

import java.util.Objects;

public class Subject {
    private int id;
    private String name;
    private String hours;

    public Subject(int id, String name, String hours) {
        this.id = id;
        this.name = name;
        this.hours = hours;
    }

    public Subject(String subject) {
        String[] vals = subject.split("\\|");
        if (!vals[0].equals("null")) this.id = Integer.parseInt(vals[0]);
        if (!vals[1].equals("null")) this.name = vals[1];
        if (!vals[2].equals("null")) this.hours = vals[2];
    }

    public Subject() {
    }

    @Override
    public String toString() {
        return id +
                "|" + name +
                "|" + hours + "|";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setNameServer(ServerConnection conn, String name) {
        this.name = name;
        conn.sendString("Subject|setName|" + id + "|" + name);
    }
    public void setHoursServer(ServerConnection conn, String hours) {
        this.hours = hours;
        conn.sendString("Subject|setHours|" + id + "|" + hours);
    }

    public void deleteServer(ServerConnection conn) {
        conn.sendString("Subject|delete|" + id + "|" + name);
    }

}
