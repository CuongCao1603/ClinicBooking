package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connect.DBContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import model.*;

public class ServiceDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext dbc = new DBContext();
    Connection connection = null;

    public List<Service> getRandomTop6Service() throws SQLException, IOException {
        List<Service> list = new ArrayList<>();
        String sql = "select concat_ws(cs.id,s.category_id)id ,cs.name,cs.setting_id,"
                + "cs.status,s.service_id,s.title,s.fee,s.description,s.img "
                + "from g3_cbs_db_final.service s "
                + "inner join g3_cbs_db_final.category_service cs "
                + "on s.category_id = cs.id ORDER BY RAND() LIMIT 6";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(9);
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                Setting s = new Setting(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
                list.add(new Service(s, rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getString(8), base64Image));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public ArrayList<Service> getAllService() throws SQLException, IOException {
        ArrayList<Service> list = new ArrayList<>();
        String sql = "SELECT s.title, cs.name, sum(r.star)/count(r.star), count(r.feedback), s.fee, s.description, s.service_id, s.img  FROM g3_cbs_db_final.service s \n"
                + "left join g3_cbs_db_final.ratestar r\n"
                + "on s.service_id = r.service_id\n"
                + "inner join g3_cbs_db_final.category_service cs\n"
                + "on cs.id  = s.category_id\n"
                + "group by s.title, cs.name,   s.fee, s.description, s.service_id";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(8);
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                RateStar r = new RateStar(rs.getInt(3), rs.getInt(4));
                Setting s = new Setting();
                s.setSetting_name(rs.getString(2));
                list.add(new Service(rs.getString(1), s, r, rs.getDouble(5), rs.getString(6), rs.getInt(7), base64Image));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public ArrayList<Service> getAllSpeciality() throws SQLException {
        ArrayList<Service> list = new ArrayList<>();
        String sql = "SELECT cs.name, cs.id  FROM g3_cbs_db_final.service s \n"
                + "inner join g3_cbs_db_final.category_service cs\n"
                + "on cs.id  = s.category_id\n"
                + "group by cs.name, cs.id";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Service(rs.getString(2), rs.getString(1)));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public ArrayList<Service> Search(String search) throws SQLException {
        ArrayList<Service> list = new ArrayList<>();
        String sql = "SELECT s.title, cs.name, sum(r.star)/count(r.star), count(r.feedback), s.fee, s.description, s.service_id, s.img FROM g3_cbs_db_final.service s \n"
                + "left join g3_cbs_db_final.ratestar r\n"
                + "on s.service_id = r.service_id\n"
                + "inner join g3_cbs_db_final.category_service cs\n"
                + "on cs.id  = s.category_id\n"
                + "group by s.title, cs.name,   s.fee, s.description, s.service_id\n"
                + "Having s.title like '%" + search + "%'";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(8);
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                RateStar r = new RateStar(rs.getInt(3), rs.getInt(4));
                Setting s = new Setting();
                s.setSetting_name(rs.getString(2));
                list.add(new Service(rs.getString(1), s, r, rs.getDouble(5), rs.getString(6), rs.getInt(7), base64Image));
            }

        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public ArrayList<Service> getServiceFiltered(String filter, String sort) throws SQLException, IOException {
        ArrayList<Service> list = new ArrayList<>();
        String sql = "SELECT s.title, cs.name, sum(r.star)/count(r.star), count(r.feedback), s.fee, s.description, cs.id, s.service_id, s.img  FROM g3_cbs_db_final.service s \n"
                + "                left join doctris_system.ratestar r\n"
                + "                on s.service_id = r.service_id\n"
                + "                inner join g3_cbs_db_final.category_service cs\n"
                + "                on cs.id  = s.category_id\n"
                + "                group by s.title, cs.name, cs.id,  s.fee, s.description, s.service_id\n"
                + "                Having cs.id = " + filter + sort;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(8);
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                RateStar r = new RateStar(rs.getInt(3), rs.getInt(4));
                Setting s = new Setting();
                s.setSetting_name(rs.getString(2));
                list.add(new Service(rs.getString(1), s, r, rs.getDouble(5), rs.getString(6), rs.getInt(7), base64Image));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public Service getServiceById(String id) throws SQLException, IOException {
        Service s = new Service();
        String sql = "SELECT s.title, cs.name, sum(r.star)/count(r.star), count(r.feedback), s.fee, s.description, s.service_id, s.img FROM g3_cbs_db_final.service s \n"
                + "left join g3_cbs_db_final.ratestar r\n"
                + "on s.service_id = r.service_id\n"
                + "inner join g3_cbs_db_final.category_service cs\n"
                + "on cs.id  = s.category_id\n"
                + "group by s.title, cs.name,   s.fee, s.description, s.service_id\n"
                + "Having s.service_id = " + id;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(8);
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                RateStar r = new RateStar(rs.getInt(3), rs.getInt(4));
                Setting se = new Setting();
                se.setSetting_name(rs.getString(2));
                s = new Service(rs.getString(1), se, r, rs.getDouble(5), rs.getString(6), rs.getInt(7), base64Image);
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return s;
    }

    public List<RateStar> getRateService(int id) throws SQLException, IOException {
        List<RateStar> list = new ArrayList<>();
        String sql = "SELECT users.name, users.img, ratestar.star, ratestar.feedback, ratestar.datetime \n"
                + "FROM ratestar inner join users \n"
                + "on ratestar.username = users.username\n"
                + "where ratestar.service_id = ?\n"
                + "order by ratestar.id desc";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String base64Image = null;
                Blob blob = rs.getBlob(2);
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                } else {
                    base64Image = "default";
                }
                Account a = new Account(rs.getString(1), null, null, base64Image);
                list.add(new RateStar(a, rs.getInt(3), rs.getString(4), rs.getTimestamp(5)));
            }
        } catch (SQLException e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public ArrayList<Service> getListByPage(ArrayList<Service> list,
            int start, int end) {
        ArrayList<Service> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
}
