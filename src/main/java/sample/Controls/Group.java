package sample.Controls;

import sample.Connectivity.ServerConnection;

public class Group {
    private int id;
    private String level;

    public Group(int id, String level) {
        this.id = id;
        this.level = level;
    }

    public Group(String group) {
        String[] vals = group.split("\\|");
        if (!vals[0].equals("null")) this.id = Integer.parseInt(vals[0]);
        if (!vals[1].equals("null")) this.level = vals[1];
    }

    public Group() {
    }

    @Override
    public String toString() {
        return id + "|" + level + "|";
    }

    public int getId() {
        return id;
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

    public void deleteServer(ServerConnection conn) {
        conn.sendString("Group|delete|" + id + "|" + level);
    }

}
