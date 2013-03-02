package de.puzzles.core;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * @author Patrick Groß-Holtwick
 * Date: 28.02.13
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseConnector {

    private static DatabaseConnector INSTANCE = new DatabaseConnector();
    private Connection dbConnection = null; // connection vorbereiten

    private DatabaseConnector() {
        try {
            // einen JDBC-Treiber registrieren und über den registrierten
            // Treiber das Programm mit der DB verbinden:
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/puzzles", "root", "");
        }
        catch (ClassNotFoundException err) {
            System.out.println("DB-Driver nicht gefunden!");
            System.out.println(err);
        }
        catch (SQLException err) {
            System.out.println("Connect nicht möglich");
            System.out.println(err);
        }

    }

    public static DatabaseConnector getInstance() {
        return INSTANCE;
    }

    public Integer checkLogin(String user, String pw) {
        String cryptedPassword = MD5(pw);
        if (cryptedPassword != null) {
            try {
                // use PreparedStatement to prevent SQL-Injection (hopefully :))
                PreparedStatement stmt = dbConnection.prepareStatement("SELECT id, password FROM consultants WHERE username LIKE ?");
                stmt.setString(1, user);
                stmt.execute();
                ResultSet result = stmt.getResultSet();
                if (result.next() && result.isLast()) {
                    if (result.getString(2).equals(cryptedPassword)) {
                        return result.getInt(1);
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes(Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
        catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException while creating MD5 hash");
            System.out.println(e);
        }
        return null;
    }

}
