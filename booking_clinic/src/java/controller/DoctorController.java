/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.Setting;

/**
 *
 * @author DELL
 */
public class DoctorController extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
        
        DoctorDAO doctordao = new DoctorDAO();
        PatientDAO patientdao = new PatientDAO();
        AppointmentDAO appointmentdao = new AppointmentDAO();
        String url = null;
        
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        Account user = (Account) session.getAttribute("user");
        
        List<Doctor> getdoctor = null;
        ArrayList<Doctor> doctorall = new ArrayList<>();
        try {

            if (action.equals("mypatient")) {
                int doctor_id = doctordao.getDoctorIDByUsername(user.getUsername());
                List<Patient> patients = patientdao.getPatientByDoctor(doctor_id);
                request.setAttribute("patients", patients);
                request.getRequestDispatcher("mypatients.jsp").forward(request, response);
            }

            if (action.equals("detailpatient")) {
                int doctor_id = doctordao.getDoctorIDByUsername(user.getUsername());
                int patient_id = Integer.parseInt(request.getParameter("id"));

                Patient patients = patientdao.getPatientbyid(patient_id);
                List<model.Appointment> appointmentlist = appointmentdao.getAppointmentByPatient(doctor_id, patient_id);

                request.setAttribute("patients", patients);
                request.setAttribute("appointmentlist", appointmentlist);

                request.getRequestDispatcher("mypatientdetails.jsp").forward(request, response);
            }

            if (action.equals("myappointment")) {
                List<Appointment> getAppointment = doctordao.getAllAppointment(doctordao.getDoctorIDByUsername(user.getUsername()));
                int page, numperpage = 8;
                int size = getAppointment.size();
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
                List<Appointment> AppointmentList = appointmentdao.getListByPage(getAppointment, start, end);
                request.setAttribute("page", page);
                request.setAttribute("num", num);
                request.setAttribute("AppointmentList", AppointmentList);
                request.getRequestDispatcher("myappointment.jsp").forward(request, response);
            }
        } catch (Exception e) {
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
