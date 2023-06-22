
package dal;

import context.DBContext;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import model.Account;
import model.Appointment;
import model.Patient;

/**
 *
 * @author DELL
 */
public class PatientDAO {

    PreparedStatement ps = null;
    Connection connection = null;
    DBContext dbc = new DBContext();
    ResultSet rs = null;

    public List<Patient> getPatientByDoctor(int doctor_id) throws SQLException {

        List<Patient> list = new ArrayList<>();
        String sql = "select distinct users.name, users.phone, users.email, a.pdate, patient.DOB, patient.patient_id\n"
                + "as lastbooking from appointments \n"
                + "inner join patient on appointments.patient_id = patient.patient_id \n"
                + "inner join users on patient.username = users.username inner join \n"
                + "(\n"
                + "select patient_id as pid , max(date) as pdate from appointments group by patient_id\n"
                + ") \n"
                + "as a on a.pid = appointments.patient_id where appointments.doctor_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, doctor_id);
            rs = ps.executeQuery();
            while (rs.next()) {               
                Account a = new Account(rs.getString(1), rs.getInt(2), rs.getString(3));
                Appointment ap = new Appointment(rs.getDate(4));
                list.add(new Patient(a, ap, rs.getDate(5), rs.getInt(6)));
            }
        } catch (Exception e) {
        }
        return list;
    }

}
