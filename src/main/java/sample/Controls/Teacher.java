package sample.Controls;

import sample.Connectivity.ServerConnection;

public class Teacher {
    private int id;
    private String name;
    private String surname;
    private String patronymic;

    public Teacher(int id, String name, String surname, String patronymic) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public Teacher(String teacher) {
        String[] vals = teacher.split("\\|");
        if (!vals[0].equals("null")) this.id = Integer.parseInt(vals[0]);
        if (!vals[1].equals("null")) this.name = vals[1];
        if (!vals[2].equals("null")) this.surname = vals[2];
        if (!vals[3].equals("null")) this.patronymic = vals[3];
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return id +
                "|" + name +
                "|" + surname +
                "|" + patronymic + "|";
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setNameServer(ServerConnection conn, String name) {
        this.name = name;
        conn.sendString("Teacher|setName|" + id + "|" + name);
    }
    public void setSurnameServer(ServerConnection conn, String surname) {
        this.surname = surname;
        conn.sendString("Teacher|setSurname|" + id + "|" + surname);
    }
    public void setPatronymicServer(ServerConnection conn, String patronymic) {
        this.patronymic = patronymic;
        conn.sendString("Teacher|setPatronymic|" + id + "|" + patronymic);
    }


    public void deleteServer(ServerConnection conn) {
        conn.sendString("Teacher|delete|" + id + "|" + name);
    }

}
