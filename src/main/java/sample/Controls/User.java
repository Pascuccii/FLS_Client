package sample.Controls;

import sample.Connectivity.DatabaseConnection;
import sample.Connectivity.ServerConnection;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

public class User implements Serializable {
    public static final long serialVersionUID = 10L;
    private boolean encrypted;
    private int id;
    private int accessMode;
    private String username;
    private String password;
    private String email;
    private String theme;
    private String language;

    public User() {
        this.id = 0;
        this.accessMode = 0;
        this.username = "";
        this.password = "";
        this.email = "";
        this.theme = "Dark";
        this.language = "English";
    }

    public User(String user, boolean encrypted) {
        this.encrypted = encrypted;
        //25 0 anton anton  Dark English
        String[] vals = user.split("\\|");
        for (String s : vals)
            System.out.println(s);
        this.id = Integer.parseInt(vals[0]);
        this.accessMode = Integer.parseInt(vals[1]);
        this.username = vals[2];
        this.password = vals[3];
        if (!encrypted) encryptPassword();
        this.email = vals[4];
        this.theme = vals[5];
        this.language = vals[6];
    }

    public User(String username, String password, boolean encrypted) {
        this.encrypted = encrypted;
        this.id = 0;
        this.accessMode = 0;
        this.username = username;
        this.password = password;
        if (!encrypted) encryptPassword();
        this.email = "";
        this.theme = "Dark";
        this.language = "English";
    }

    public User(int id, int accessMode, String username, String password, String email, boolean encrypted) {
        this.encrypted = encrypted;
        this.id = id;
        this.accessMode = accessMode;
        this.username = username;
        this.password = password;
        if (!encrypted) encryptPassword();
        this.email = email;
        this.theme = "Dark";
        this.language = "English";
    }

    public User(int id, int accessMode, String username, String password, String email, String theme, String language, boolean encrypted) {
        this.encrypted = encrypted;
        this.id = id;
        this.accessMode = accessMode;
        this.username = username;
        this.password = password;
        if (!encrypted) encryptPassword();
        this.email = email;
        this.theme = theme;
        this.language = language;
    }

    public void setEMail(String email) {
        this.email = (null == email) ? "" : email;
    }

    //for DB settings
    public void setAccessModeDB(DatabaseConnection conn, int accessMode) throws SQLException {
        this.accessMode = accessMode;
        String prepStat = "UPDATE users SET id = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
        preparedStatement.setInt(2, this.id);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public void setUsernameDB(DatabaseConnection conn, String username) throws SQLException {
        this.username = (null == username) ? "" : username;
        String prepStat = "UPDATE users SET name = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
        preparedStatement.setInt(2, this.id);
        preparedStatement.setString(1, username);
        preparedStatement.execute();
    }

    public void setPasswordDB(DatabaseConnection conn, String password) throws SQLException {
        this.password = (null == password) ? "" : password;
        encryptPassword();
        String prepStat = "UPDATE users SET password = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
        preparedStatement.setInt(2, this.id);
        preparedStatement.setString(1, password);
        preparedStatement.execute();
    }

    public void setEMailDB(DatabaseConnection conn, String email) throws SQLException {
        this.email = (null == email) ? "" : email;
        String prepStat = "UPDATE users SET email = ? WHERE id = ?";
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
        preparedStatement.setInt(2, this.id);
        preparedStatement.setString(1, email);
        preparedStatement.execute();
    }

    public void setThemeDB(DatabaseConnection conn, String theme) throws SQLException {
        String prepStat = "UPDATE user_configs SET theme = ? WHERE userId = ?";
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
        preparedStatement.setInt(2, this.id);
        theme = theme.trim();
        theme = theme.toLowerCase();
        switch (theme) {
            case "light":
                this.theme = "Light";
                preparedStatement.setString(1, theme);
                break;
            default:
                this.theme = "Dark";
                preparedStatement.setString(1, theme);
                break;
        }
        preparedStatement.execute();
    }

    public void setLanguageDB(DatabaseConnection conn, String language) throws SQLException {
        String prepStat = "UPDATE user_configs SET language = ? WHERE userId = ?";
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
        preparedStatement.setInt(2, this.id);

        language = language.trim();
        language = language.toLowerCase();
        switch (language) {
            case "russian":
                this.language = "Russian";
                preparedStatement.setString(1, language);
                break;
            default:
                this.language = "English";
                preparedStatement.setString(1, language);
                break;
        }
        preparedStatement.execute();
    }

    public int getId() {
        return id;
    }

    //for loading only
    public void setId(int id) { //for init load only
        this.id = id;
    }

    public int getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(int accessMode) {
        this.accessMode = accessMode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = (null == username) ? "" : username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = (null == password) ? "" : password;
        encryptPassword();
    }

    public String getEmail() {
        return email;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        theme = theme.trim();
        theme = theme.toLowerCase();
        switch (theme) {
            case "light":
                this.theme = "Light";
                break;
            default:
                this.theme = "Dark";
                break;
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        language = language.trim();
        language = language.toLowerCase();
        switch (language) {
            case "russian":
                this.language = "Russian";
                break;
            default:
                this.language = "English";
                break;
        }
    }

    public void setAccessModeServer(ServerConnection conn, String value) {
        this.accessMode = Integer.parseInt(value);
        if (value == null || value.equals(""))
            value = "null";
        conn.sendString("User|setAccessMode|" + id + "|" + value);
    }

    public void setUsernameServer(ServerConnection conn, String value) {
        this.username = value;
        if (value == null || value.equals(""))
            value = "null";
        conn.sendString("User|setUsername|" + id + "|" + value);
    }

    public void setPasswordServer(ServerConnection conn, String value) {
        if (value.matches("[0-9a-zA-Z]{6,}")) {
            this.password = value;
            encrypted = false;
            encryptPassword();
            conn.sendString("User|setPassword|" + id + "|" + this.password);
        }
    }

    public void setEMailServer(ServerConnection conn, String value) {
        this.email = value;
        if (value == null || value.equals(""))
            value = "null";
        conn.sendString("User|setEMail|" + id + "|" + value);
    }

    public void setThemeServer(ServerConnection conn, String value) {
        this.theme = value;
        if (value == null || value.equals(""))
            value = "null";
        conn.sendString("User|setTheme|" + id + "|" + value);
    }

    public void setLanguageServer(ServerConnection conn, String value) {
        this.language = value;
        if (value == null || value.equals(""))
            value = "null";
        conn.sendString("User|setLanguage|" + id + "|" + value);
    }

    public void deleteServer(ServerConnection conn) {
        conn.sendString("User|delete|" + id + "|" + username);
    }

    public String encryptPassword() {
        String oldPass;
        if (!encrypted)
            try {
                this.encrypted = true;
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                oldPass = password;
                this.password = Base64.getEncoder().encodeToString(hash);
                System.out.println("OLD PASS: " + oldPass + "   ENCR PASS: " + this.password);
            } catch (NoSuchAlgorithmException e) {
                System.err.println(e);
            }
        return this.password;
    }

    @Override
    public String toString() {
        return id +
                "|" + accessMode +
                "|" + username +
                "|" + password +
                "|" + email +
                "|" + theme +
                "|" + language;
    }
}
