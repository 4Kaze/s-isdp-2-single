package pl.lodz.p.it.isdp;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author horseburger
 */
public class DBConnector{
    private Connection con;
    
    public DBConnector(String host, String db, String username, String password) throws SQLException{
        makeConnection(host, db, username, password);
        checkForTable();
    }
    private void makeConnection(String host, String db, String username, String password) throws SQLException {
        String url = String.format("jdbc:derby://%s/%s;create=true", host, db);
        this.con = DriverManager.getConnection(url, username, password);
    }
    
    public void createSortingTable() throws SQLException {
        checkForConnection();
        Statement stmt = this.con.createStatement();
        String query = "create table sorts ("
                + "Id int NOT NULL GENERATED ALWAYS AS IDENTITY, "
                + "numbers long varchar not null, "
                + "sortDate timestamp  not null )";
        stmt.execute(query);
    }
    
    public void checkForTable() throws SQLException {
        checkForConnection();
        DatabaseMetaData meta = this.con.getMetaData();
        ResultSet tables = meta.getTables(null, null, "SORTS", null);
        if (!tables.next()){
            createSortingTable();
        }
    }
    
    public void putSortedTable(long[] tab) throws SQLException{
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        PreparedStatement insertNumbers = null;
        String insertNumbersString = "insert into sorts(numbers, sortDate)  values (?, ?)";
        con.setAutoCommit(false);
        insertNumbers = con.prepareStatement(insertNumbersString);

        StringBuilder s = new StringBuilder();
        for (long i : tab) {
            s.append(i).append(',');
        }
        
        insertNumbers.setString(1, s.toString());
        insertNumbers.setTimestamp(2, date);
        insertNumbers.execute();
        con.commit();
    }
    
    public void checkForConnection(){
        if (this.con == null) {
            throw new NullPointerException("Connection not established");
        }
    }
    
    public void disconnect() throws SQLException {
        this.con.close();
    }
}
