/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.AppointmentDAO;
import dal.DoctorDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.*;

/**
 *
 * @author Khuong Hung
 */
@MultipartConfig(maxFileSize = 16177216)
public class DoctorManage extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        DoctorDAO doctordao = new DoctorDAO();
        String action = request.getParameter("action");
        List<Doctor> doctorlist = null;
        AppointmentDAO appointmentdao = new AppointmentDAO();
        String url = null;
        String alert = null;
        String message = null;
        List<Setting> specialitylist = null;
        try {
            specialitylist = doctordao.getSpeciality();
            if (action.equals("all")) {
                url = "doctormanage?action=all";
                doctorlist = doctordao.getAllDoctor();
            }
            if(action.equals("filter")){
                String gender = request.getParameter("gender");
                String speciality = request.getParameter("speciality");
                request.setAttribute("gender",gender);
                request.setAttribute("speciality1",speciality);
                if(gender.equals("all") && speciality.equals("all")){
                    response.sendRedirect("doctormanage?action=all");
                }else if(gender.equals("all")){
                    doctorlist = doctordao.getAllDoctorBySpeciality(speciality);
                }else if(speciality.equals("all")){
                    doctorlist = doctordao.getAllDoctorByGender(gender);
                }else{
                    doctorlist = doctordao.getAllDoctorByFilter(gender, speciality);
                }
                url = "doctormanage?action=filter&gender=" + gender + "&speciality=" + speciality;
            }
            if(action.equals("search")){
                String text = request.getParameter("txt");
                doctorlist = doctordao.Search(text);
                url = "doctormanage?action=search&txt=" + text;
            }
            if(action.equals("detail")){
                int doctor_id = Integer.parseInt(request.getParameter("id"));
                Doctor doctor = new Doctor();
                List<model.Appointment> appointmentlist = appointmentdao.getAppointmentByDoctor(doctor_id);
                List<RateStar> getRate = doctordao.getRateDoctor(doctor_id);
                doctor = doctordao.getDoctorByID(doctor_id);
                request.setAttribute("speciality", specialitylist);
                request.setAttribute("appointment", appointmentlist);
                request.setAttribute("rate", getRate);
                request.setAttribute("doctor", doctor);
                request.getRequestDispatcher("admin/doctordetail.jsp").forward(request, response);
            }
            if (action.equals("update_image")) {
                int doctor_id = Integer.parseInt(request.getParameter("id"));
                Part image = request.getPart("image");
                if (image != null) {
                    try {
                        doctordao.UpdateImage(doctor_id, image);
                    } catch (Exception e) {
                    }
                }
                alert = "success";
                message = "Cập nhật ảnh thành công";
                request.setAttribute("alert", alert);
                request.setAttribute("message", message);
                request.getRequestDispatcher("doctormanage?action=detail&id=" + doctor_id).forward(request, response);
            }
            if(action.equals("update_info")){
                int doctor_id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
                int phone = Integer.parseInt(request.getParameter("phone"));
                Date DOB = Date.valueOf(request.getParameter("DOB"));
                String description = request.getParameter("description");
                int speciality = Integer.parseInt(request.getParameter("speciality"));
                boolean status = Boolean.parseBoolean(request.getParameter("status"));
                doctordao.DoctorUpdate(doctor_id, name, gender, phone, DOB, description, speciality, status);
                alert = "success";
                message = "Cập nhật thông tin thành công";
                request.setAttribute("alert", alert);
                request.setAttribute("message", message);
                request.getRequestDispatcher("doctormanage?action=detail&id=" + doctor_id).forward(request, response);
            }
            if (doctorlist != null) {
                int page, numperpage = 8;
                int type = 0;
                int size = doctorlist.size();
                int num = (size % 8 == 0 ? (size / 8) : ((size / 8)) + 1);
                String xpage = request.getParameter("page");
                if (xpage == null) {
                    page = 1;
                } else {
                    page = Integer.parseInt(xpage);
                }
                int start, end;
                start = (page - 1) * numperpage;
                end = Math.min(page * numperpage, size);
                List<Doctor> doctors = doctordao.getListByPage(doctorlist, start, end);
                request.setAttribute("type", type);
                request.setAttribute("page", page);
                request.setAttribute("num", num);
                request.setAttribute("url", url);
                request.setAttribute("doctor", doctors);
                request.setAttribute("speciality", specialitylist);
                request.getRequestDispatcher("admin/doctor.jsp").forward(request, response);
            }
        } catch (IOException | SQLException | ServletException e) {
            System.out.println(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
