package de.puzzles.core;

import de.puzzles.core.domain.CreditRequest;
import de.puzzles.core.domain.CreditState;
import de.puzzles.core.domain.Customer;
import de.puzzles.core.domain.Transaction;
import de.puzzles.core.util.PuzzlesUtils;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE id=?";
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            if (result.next() && result.isLast()) {
                Customer customer = new Customer();
                customer.setFirstname(result.getString(2));
                customer.setLastname(result.getString(3));
                customer.setBirthday(new DateTime(result.getDate(4)));
                customer.setStreet(result.getString(5));
                customer.setCity(result.getString(6));
                customer.setZipcode(result.getString(7));
                customer.setTelephone(result.getString(8));
                customer.setEmail(result.getString(9));
                customer.setAccountnumber(result.getString(10));
                customer.setBankcode(result.getString(11));
                return customer;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CreditRequest getCreditRequestById(int id) {
        String sql = "SELECT * FROM creditrequests WHERE id=?";
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            if (result.next() && result.isLast()) {
                CreditRequest request = new CreditRequest();
                request.setConsultantId(result.getInt("consultant_id"));
                request.setCreationDate(new DateTime(result.getDate("creationdate")));
                request.setState(PuzzlesUtils.getCreditStateByValue(result.getInt("state")));
                request.setAmount(result.getDouble("creditamount"));
                request.setRate(result.getDouble("rate"));
                request.setDuration(result.getInt("duration"));
                request.setCustomer(getCustomerById(result.getInt("customer_id")));
                request.setTransactions(getTransactionsByRequestId(id));
                return request;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> getTransactionsByRequestId(int id) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        String sql = "SELECT * FROM transactions WHERE request_id=?";
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            while (result.next()) {
                transactions.add(new Transaction(null,
                                                 id,
                                                 result.getString("description"),
                                                 result.getString("description1"),
                                                 result.getString("description2"),
                                                 result.getDouble("value")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void changeRequestState(int id, CreditState state) {
        try {
            String sql = "UPDATE creditrequests SET state=? WHERE id =?";
            PreparedStatement stmt = dbConnection.prepareStatement(sql);
            stmt.setInt(1,state.ordinal());
            stmt.setInt(2,id);
            stmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer saveCreditrequest(CreditRequest req) {
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
                stmt = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, customerId);
                stmt.setInt(2, req.getConsultantId());
                stmt.setDate(3, new Date(System.currentTimeMillis()));
                stmt.setInt(4, req.getState().ordinal());
                stmt.setFloat(5, req.getAmount().floatValue());
                stmt.setBoolean(6, req.hasFixedLength());
                stmt.setFloat(7, req.getRate().floatValue());
                stmt.setObject(8, req.getDuration());

                stmt.execute();
                result = stmt.getGeneratedKeys();
                if (result.next() && result.isLast()) {
                    int requestId = result.getInt(1);

                    for (Transaction transaction : req.getTransactions()) {
                        sql = "insert into transactions values (null,?,?,?,?,?)";
                        stmt = dbConnection.prepareStatement(sql);
                        stmt.setInt(1, requestId);
                        stmt.setString(2, transaction.getDescription());
                        stmt.setString(3, transaction.getDescription1());
                        stmt.setString(4, transaction.getDescription2());
                        stmt.setDouble(5, transaction.getValue());
                        stmt.execute();
                    }
                    return requestId;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
