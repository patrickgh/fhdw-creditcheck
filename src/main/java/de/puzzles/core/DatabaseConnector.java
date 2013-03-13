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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 28.02.13
 *         Time: 12:15
 *         To change this template use File | Settings | File Templates.
 */
public class DatabaseConnector {

    public static final String DEFAULT_URL = "jdbc:mysql://localhost/puzzles";
    public static final String DEFAULT_USER = "root";
    public static final String DEFAULT_PASSWORD = "";
    private static DatabaseConnector INSTANCE = new DatabaseConnector();
    private Connection dbConnection; // connection vorbereiten

    private DatabaseConnector() {
        try {
            //Default Werte nutzen, falls keine Parameter mitgegeben werden
            String url = System.getProperty("db.url") != null ? System.getProperty("db.url") : DEFAULT_URL;
            String user = System.getProperty("db.user") != null ? System.getProperty("db.user") : DEFAULT_USER;
            String password = System.getProperty("db.password") != null ? System.getProperty("db.password") : DEFAULT_PASSWORD;
            // einen JDBC-Treiber registrieren und über den registrierten
            // Treiber das Programm mit der DB verbinden:
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(url, user, password);
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
                    if (result.getString("password").equals(cryptedPassword)) {
                        return result.getInt("id");
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
                customer.setFirstname(result.getString("firstname"));
                customer.setLastname(result.getString("lastname"));
                customer.setBirthday(new Date(result.getDate("birthdate").getTime()));
                customer.setStreet(result.getString("street"));
                customer.setCity(result.getString("city"));
                customer.setZipcode(result.getString("zipcode"));
                customer.setTelephone(result.getString("telephone"));
                customer.setEmail(result.getString("email"));
                customer.setAccountnumber(result.getString("accountnumber"));
                customer.setBankcode(result.getString("bankcode"));
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
                request.setId(result.getInt("id"));
                request.setConsultantId(result.getInt("consultant_id"));
                request.setCreationDate(result.getDate("creationdate"));
                request.setState(PuzzlesUtils.getCreditStateByValue(result.getInt("state")));
                request.getRepaymentPlan().setAmount(result.getDouble("creditamount"));
//                request.getRepaymentPlan().setRate(result.getDouble("rate"));
                request.getRepaymentPlan().setDuration(result.getDouble("duration"));
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
            stmt.setInt(1, state.ordinal());
            stmt.setInt(2, id);
            stmt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getConsultantNames() {
        Map<String, Integer> names = new HashMap<String, Integer>();
        try {
            String sql = "SELECT CONCAT(firstname, ' ', lastname) AS name, id FROM consultants ORDER BY name";
            PreparedStatement stmt = dbConnection.prepareStatement(sql);
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            while (result.next()) {
                names.put(result.getString("name"), result.getInt("id"));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public List<CreditRequest> getCreditRequest(int id, String customer, java.util.Date start, java.util.Date end, Integer skip, Integer limit, String sort) {
        List<CreditRequest> resultList = new ArrayList<CreditRequest>();
        if (start == null) {
            start = new DateTime().minusYears(10).toDate();
        }
        if (end == null) {
            end = new DateTime().plusDays(2).toDate();
        }
        if (limit == null) {
            limit = 1000;
        }
        if (skip == null) {
            skip = 0;
        }
        if (sort == null) {
            sort = "creationdate ASC";
        }
        String sql;
        PreparedStatement stmt;
        try {
            if (customer != null && customer.length() > 0) {
                sql = "SELECT creditrequests.id AS id " +
                      "FROM creditrequests LEFT JOIN customer ON creditrequests.customer_id = customer.id " +
                      "WHERE consultant_id=? " +
                      "AND (" +
                      "customer.firstname LIKE ? OR customer.lastname LIKE ? " +
                      ")" +
                      "AND creationdate < ? AND creationdate > ? " +
                      "ORDER BY " + sort +
                      " LIMIT ?,?";
                stmt = dbConnection.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.setString(2, "%" + customer + "%");
                stmt.setString(3, "%" + customer + "%");
                stmt.setDate(4, new Date(end.getTime()));
                stmt.setDate(5, new Date(start.getTime()));
                stmt.setInt(6, skip);
                stmt.setInt(7, limit);
            }
            else {
                sql = "SELECT creditrequests.id AS id " +
                      "FROM creditrequests LEFT JOIN customer ON creditrequests.customer_id = customer.id " +
                      "WHERE consultant_id=? " +
                      "AND creationdate < ? AND creationdate > ? " +
                      "ORDER BY " + sort +
                      " LIMIT ?,?";
                stmt = dbConnection.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.setDate(2, new Date(end.getTime()));
                stmt.setDate(3, new Date(start.getTime()));
                stmt.setInt(4, skip);
                stmt.setInt(5, limit);
            }
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            while (result.next()) {
                resultList.add(getCreditRequestById(result.getInt("id")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public Double getLivingCosts(Integer persons) {
        Double result = 0.0;
        try {
            String sql = "SELECT value FROM config WHERE category LIKE ?";
            PreparedStatement stmt;
            ResultSet resultSet;
            if (persons == 1) {
                stmt = dbConnection.prepareStatement(sql);
                stmt.setString(1, "LHKP1");
                stmt.execute();
                resultSet = stmt.getResultSet();
                if (resultSet.next() && resultSet.isLast()) {
                    result += Double.valueOf(resultSet.getString("value"));
                }
            }
            else if (persons > 1) {
                stmt = dbConnection.prepareStatement(sql);
                stmt.setString(1, "LHKP2");
                stmt.execute();
                resultSet = stmt.getResultSet();
                if (resultSet.next() && resultSet.isLast()) {
                    result += Double.valueOf(resultSet.getString("value"));
                }
                if (persons > 2) {
                    stmt = dbConnection.prepareStatement(sql);
                    stmt.setString(1, "LHKP3");
                    stmt.execute();
                    resultSet = stmt.getResultSet();
                    if (resultSet.next() && resultSet.isLast()) {
                        result += (Double.valueOf(resultSet.getString("value")) * (persons - 2));
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Double> getCarCostTypes() {
        LinkedHashMap<String, Double> result = new LinkedHashMap<String, Double>();
        String sql = "SELECT * FROM config WHERE category LIKE 'KFZ'";
        try {
            Statement stmt = dbConnection.createStatement();
            stmt.execute(sql);
            ResultSet resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                result.put(resultSet.getString("description"), Double.valueOf(resultSet.getString("value")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Double getBaseInterest() {
        Double interest = null;
        try {
            Statement stmt = dbConnection.createStatement();
            stmt.execute("SELECT value FROM config WHERE category LIKE 'BASE_INTEREST'");
            ResultSet result = stmt.getResultSet();
            if(result.next() && result.isLast()) {
                String temp = result.getString("value");
                interest = Double.valueOf(temp) / 100.0;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return interest;
    }

    public Integer saveCreditrequest(CreditRequest req) {
        Customer customer = req.getCustomer();
        try {
            String sql = "insert into customer values (null,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getFirstname());
            stmt.setString(2, customer.getLastname());
            stmt.setDate(3, new Date(customer.getBirthday().getTime()));
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

                sql = "insert into creditrequests values(null,?,?,?,?,?,?,?,?,?)";
                stmt = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, customerId);
                stmt.setInt(2, req.getConsultantId());
                stmt.setDate(3, new Date(System.currentTimeMillis()));
                stmt.setInt(4, req.getState().ordinal());
                stmt.setFloat(5, req.getRepaymentPlan().getAmount().floatValue());
                stmt.setBoolean(6, true);
                stmt.setFloat(7, 0.0F);//req.getRepaymentPlan().getRate().floatValue());
                stmt.setDouble(8, req.getRepaymentPlan().getDuration());
                stmt.setFloat(9, req.getRepaymentPlan().getInterest().floatValue());

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
