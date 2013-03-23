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
 * This class handles the database connection and provides the methods for accessing the data. It is build as a java singleton
 * so that there is only one database connection open at any time.
 *
 * @author Patrick Groß-Holtwick
 */
public final class DatabaseConnector {

    public static final String DEFAULT_URL = "jdbc:mysql://localhost/puzzles";
    public static final String DEFAULT_USER = "root";
    public static final String DEFAULT_PASSWORD = "";
    public static final String VALUE = "value";
    private static final DatabaseConnector INSTANCE = new DatabaseConnector();
    private Connection dbConnection;

    /**
     * Constructor which creates the Connection object. It is private so you can not create an own instance.
     */
    private DatabaseConnector() {
        try {
            //use credentials from properties, if available, otherwise use defaults
            String url = System.getProperty("db.url") != null ? System.getProperty("db.url") : DEFAULT_URL;
            String user = System.getProperty("db.user") != null ? System.getProperty("db.user") : DEFAULT_USER;
            String password = System.getProperty("db.password") != null ? System.getProperty("db.password") : DEFAULT_PASSWORD;
            // register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // create connection
            dbConnection = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException err) {
            System.out.println("DB-Driver nicht gefunden!");
            err.printStackTrace();
        }
        catch (SQLException err) {
            System.out.println("Connect nicht möglich");
            err.printStackTrace();
        }
    }

    /**
     * Returns an instance of this class. Since the constructor is private, this is the only way to use this class.
     * @return instance of the DatabaseConnector
     */
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

    /**
     * gets an Customer object from the database with a given id
      * @param id id of the customer
     * @return customer object
     */
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

    /**
     * reads a credit-request object from the database.
     * @param id id of the request
     * @return CreditRequest object
     */
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

    /**
     * gets a list of all transactions (incomes & spendings) of a credit-request
     * @param id id of the request
     * @return a list with Transaction objects
     */
    private List<Transaction> getTransactionsByRequestId(int id) {
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
                                                 result.getDouble(VALUE)));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    /**
     * changes the state of a credit request in the database
     * @param id id of the request
     * @param state the new state
     */
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

    /**
     * gets all available consultant names and their ids.
     * @return a map with the name as keys and the id as values.
     */
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

    /**
     * gets the consultant name of a given consultant id.
     * @param id the id of the consultant
     * @return a string with the name
     */
    public String getConsultantNameById(Integer id) {
        String name = "";
        try {
            String sql = "SELECT CONCAT(firstname, ' ', lastname) AS name, id FROM consultants WHERE id = ?";
            PreparedStatement stmt = dbConnection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            if (result.next() && result.isLast()) {
                name = result.getString("name");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * gets all credit requests which match the given conditions
     * @param id the consultant id
     * @param customer the customer name (or a part of it)
     * @param start the start date
     * @param end the end date
     * @param skip the number of entries which should be skipped
     * @param limit the maximum number of entries which should be returned
     * @param sort the sort field
     * @return a List with CreditRequest objects
     */
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

    /**
     * calculates the living costs for a given number of persons in a household
     * @param persons the number of persons
     * @return the living costs
     */
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
                    result += Double.valueOf(resultSet.getString(VALUE));
                }
            }
            else if (persons > 1) {
                stmt = dbConnection.prepareStatement(sql);
                stmt.setString(1, "LHKP2");
                stmt.execute();
                resultSet = stmt.getResultSet();
                if (resultSet.next() && resultSet.isLast()) {
                    result += Double.valueOf(resultSet.getString(VALUE));
                }
                if (persons > 2) {
                    stmt = dbConnection.prepareStatement(sql);
                    stmt.setString(1, "LHKP3");
                    stmt.execute();
                    resultSet = stmt.getResultSet();
                    if (resultSet.next() && resultSet.isLast()) {
                        result += (Double.valueOf(resultSet.getString(VALUE)) * (persons - 2));
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * returns all available car cost types.
     * @return a map with the description as keys and the costs as values
     */
    public Map<String, Double> getCarCostTypes() {
        LinkedHashMap<String, Double> result = new LinkedHashMap<String, Double>();
        String sql = "SELECT * FROM config WHERE category LIKE 'KFZ'";
        try {
            Statement stmt = dbConnection.createStatement();
            stmt.execute(sql);
            ResultSet resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                result.put(resultSet.getString("description"), Double.valueOf(resultSet.getString(VALUE)));
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
            if (result.next() && result.isLast()) {
                String temp = result.getString(VALUE);
                interest = Double.valueOf(temp) / 100.0;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return interest;
    }

    /**
     * inserts a new CreditRequest object in the database.
     * @param req the CreditRequest object.
     * @return the generated id of the entry
     */
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
