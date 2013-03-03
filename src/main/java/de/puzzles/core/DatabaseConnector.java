package de.puzzles.core;

import de.puzzles.core.util.PuzzlesUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 28.02.13
 *         Time: 12:15
 *         To change this template use File | Settings | File Templates.
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
        String cryptedPassword = PuzzlesUtils.md5(pw);
        if (cryptedPassword != null) {
            try {
                // use PreparedStatement to prevent SQL-Injection (hopefully :))
                PreparedStatement stmt = dbConnection.prepareStatement("select id, password from consultants where username like ?");
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

    public Boolean saveCreditrequest(CreditRequest req) {
        Customer customer = req.getCustomer();
        try {
            String sql = "insert into customer values (null,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getFirstname());
            stmt.setString(2, customer.getLastname());
            stmt.setDate(3, new Date(customer.getBirthday().getMillis()));
            stmt.setString(4, customer.getStreet());
            stmt.setString(5, customer.getCity());
            stmt.setString(6, customer.getZipcode());
            stmt.setString(7, customer.getTelephone());
            stmt.setString(8, customer.getEmail());
            stmt.setString(9, customer.getAccountnumber());
            stmt.setString(10, customer.getBankcode());

            stmt.executeUpdate();
            ResultSet result = stmt.getGeneratedKeys();
            if (result.next() && result.isLast()) {
                int customerId = result.getInt(1);

                sql = "insert into creditrequests values(null,?,?,?,?,?,?,?,?)";
                stmt = dbConnection.prepareStatement(sql);
                stmt.setInt(1, customerId);
                stmt.setInt(2, req.getConsultantId());
                stmt.setDate(3, new Date(System.currentTimeMillis()));
                stmt.setInt(4, req.getState().ordinal());
                stmt.setFloat(5, req.getAmount().floatValue());
                stmt.setBoolean(6, req.hasFixedLength());
                stmt.setFloat(7, req.getRate().floatValue());
                stmt.setObject(8, req.getDuration());

                stmt.execute();
                //TODO: save transactions
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
