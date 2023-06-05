////package connect;
////
////import java.sql.Connection;
////import java.sql.DriverManager;
////import java.sql.ResultSet;
////import java.sql.SQLException;
////import java.sql.Statement;
////import java.util.logging.Level;
////import java.util.logging.Logger;
////
////public class DBContext {
////    public Connection conn = null;
////    
////    public DBContext() {
////        this("jdbc:mysql://localhost:3306/Booking2", "sa", "123456");
////    }
////    
////    public DBContext(String url, String username, String password) {
////        try {
////            Class.forName("com.mysql.cj.jdbc.Driver");
////            conn = DriverManager.getConnection(url, username, password);
////            System.out.println("Connected to the database");
////        } catch (ClassNotFoundException | SQLException ex) {
////            ex.printStackTrace();
////        }
////    }
////    
////    public ResultSet getData(String sql) {
////        ResultSet rs = null;
////        try {
////            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
////            rs = statement.executeQuery(sql);
////        } catch (SQLException ex) {
////            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        return rs;
////    }
////    
////    public static void main(String[] args) {
////        new DBContext();
////    }
////}
//
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package connect;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
///**
// *
// * @author Khuong Hung
// */
//public class DBContext {
//
//    ResourceBundle bundle = ResourceBundle.getBundle("resource.Booking2");
//
//    public Connection getConnection() {
//        try {
//            Class.forName(bundle.getString("com.mysql.jdbc.Driver"));
//            String url = bundle.getString("jdbc:mysql://localhost:3306/Booking2");
//            String username = bundle.getString("sa");
//            String password = bundle.getString("123456");
//            Connection connection = DriverManager.getConnection(url, username, password);
//            return connection;
//        } catch (ClassNotFoundException | SQLException ex) {
//            System.out.println(ex);
//        }
//        return null;
//    }
//
//    public static void main(String[] args) throws SQLException {
//        DBContext db = new DBContext();
//        System.out.println(db.getConnection());
//        System.out.println(db.getConnection().getCatalog());
//    }
//}


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
            String dbName = "doctris_system";
            String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName + "?" + condition;

            String username = "root";
            String password = "thanh298";

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
