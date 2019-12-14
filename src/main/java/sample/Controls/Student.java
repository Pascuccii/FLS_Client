package sample.Controls;

import sample.Connectivity.ServerConnection;

import java.util.Objects;

public class Student {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String groupId;
    private String email;
    private String phone;

    public Student(int id, String name, String surname, String patronymic, String groupId, String email, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.groupId = groupId;
        this.email = email;
        this.phone = phone;
    }

    public Student(String student) {
        String[] vals = student.split("\\|");
        if (!vals[0].equals("null")) this.id = Integer.parseInt(vals[0]);
        if (!vals[1].equals("null")) this.name = vals[1];
        if (!vals[2].equals("null")) this.surname = vals[2];
        if (!vals[3].equals("null")) this.patronymic = vals[3];
        if (!vals[4].equals("null")) this.groupId = vals[4];
        if (!vals[5].equals("null")) this.email = vals[5];
        if (!vals[6].equals("null")) this.phone = vals[6];
    }

    public Student() {
    }

    @Override
    public String toString() {
        return id +
                "|" + name +
                "|" + surname +
                "|" + patronymic +
                "|" + groupId +
                "|" + email +
                "|" + phone + "|";
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEmail() {
        return Objects.requireNonNullElse(email, "");
    }

    public void setEmail(String email) {
        if (email.equals(""))
            this.email = "null";
        else
            this.email = email;
    }

    public String getPhone() {
        return Objects.requireNonNullElse(phone, "");
    }

    public void setPhone(String phone) {
        if (phone.equals(""))
            this.phone = "null";
        else
            this.phone = phone;
    }

    public void setNameServer(ServerConnection conn, String name) {
        this.name = name;
        conn.sendString("Student|setName|" + id + "|" + name);
    }

    public void setSurnameServer(ServerConnection conn, String surname) {
        this.surname = surname;
        conn.sendString("Student|setSurname|" + id + "|" + surname);
    }

    public void setPatronymicServer(ServerConnection conn, String patronymic) {
        this.patronymic = patronymic;
        conn.sendString("Student|setPatronymic|" + id + "|" + patronymic);
    }

    public void setGroupIdServer(ServerConnection conn, String groupId) {
        this.groupId = groupId;
        conn.sendString("Student|setGroupId|" + id + "|" + groupId);
    }

    public void setEmailServer(ServerConnection conn, String email) {
        setEmail(email);
        conn.sendString("Student|setEmail|" + id + "|" + this.email);
    }

    public void setPhoneServer(ServerConnection conn, String phone) {
        setPhone(phone);
        conn.sendString("Student|setPhone|" + id + "|" + this.phone);
    }


    public void deleteServer(ServerConnection conn) {
        conn.sendString("Student|delete|" + id + "|" + name);
    }

}
