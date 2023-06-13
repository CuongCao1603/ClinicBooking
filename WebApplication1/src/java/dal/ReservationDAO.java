/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import model.*;

/**
 *
 * @author doans
 */
public class ReservationDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public List<Reservation> getReservationListInDay() throws SQLException, IOException {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT reservations.reservation_id, users.name, service.title,\n"
                + "reservations.date, reservations.time, reservations.status\n"
                + "FROM reservations\n"
                + "INNER JOIN service ON reservations.service_id = service.service_id\n"
                + "INNER JOIN patient ON reservations.patient_id = patient.patient_id\n"
                + "INNER JOIN users ON patient.username = users.username\n"
                + "WHERE reservations.date = CAST(CURDATE() AS DATE);";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Service service = new Service(0, rs.getString(3));
                Account account = new Account(rs.getString(2));
                Patient patient = new Patient(account);
                list.add(new Reservation(rs.getInt(1), patient, service, rs.getDate(4), rs.getTime(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            //time
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public int SumFee(String type) {
        int sum = 0;
        String month = "select sum(service.fee) from reservations inner join service on reservations.service_id = service.service_id  ";
        String today = "select sum(service.fee) from reservations inner join service on reservations.service_id = service.service_id where reservations.status = 'Complete' AND reservations.date = CURRENT_DATE";
        String day7 = "select sum(service.fee) from reservations inner join service on reservations.service_id = service.service_id where reservations.status = 'Complete' AND reservations.date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE";
        String day14 = "select sum(service.fee) from reservations inner join service on reservations.service_id = service.service_id where reservations.status = 'Complete' AND reservations.date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY) AND CURRENT_DATE";
        try {
            connection = dbc.getConnection();
            if (type.equals("7day")) {
                ps = connection.prepareStatement(day7);
            }
            if (type.equals("14day")) {
                ps = connection.prepareStatement(day14);
            }
            if (type.equals("today")) {
                ps = connection.prepareStatement(today);
            }
            if (type.equals("month")) {
                ps = connection.prepareStatement(month);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                sum = rs.getInt(1);
            }
        } catch (Exception e) {
            //Time
        }
        return sum;
    }

}
