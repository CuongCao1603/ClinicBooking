
package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class DBContext {

    private final String condition = "allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false&requireSSL=false";

    public Connection getConnection() {
        try {
            // Edit URL , username, password to authenticate with your MS SQL Server
            Class.forName("com.mysql.cj.jdbc.Driver");
            String serverName = "localhost";
            String portNumber = "3306";
            String dbName = "g3_cbs_db_final";
            String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName + "?" + condition;

            String username = "sa";
            String password = "vanhleg2301";

            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        DBContext db = new DBContext();
        System.out.println(db.getConnection());
    }
}
