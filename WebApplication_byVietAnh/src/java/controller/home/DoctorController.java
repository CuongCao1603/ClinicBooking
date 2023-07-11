/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.home;

import dal.DoctorDAO;
import dal.AppointmentDAO;
import dal.PatientDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Doctor;
import model.Patient;
import model.RateStar;
import model.Setting;
import model.Appointment;

/**
 *
 * @author Khuong Hung
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        Account user = (Account) session.getAttribute("user");

        DoctorDAO doctordao = new DoctorDAO();
        AppointmentDAO appointmentdao = new AppointmentDAO();
        PatientDAO patientdao = new PatientDAO();

        String url = null;
        List<Doctor> getdoctor = null;
        List<Patient> patients = null;
        ArrayList<Doctor> doctorall = new ArrayList<>();

        try {

            List<Setting> specialitylist = doctordao.getSpeciality();
            if (action.equals("all")) {
                url = "doctor?action=all";
                getdoctor = doctordao.getAllDoctorHome();
            }

            if (action.equals("sort")) {
                String type = request.getParameter("type");
                request.setAttribute("sort", type);
                if (type.equals("all")) {
                    response.sendRedirect("doctor?action=all");
                } else {
                    getdoctor = doctordao.getSort(type);
                }
                url = "doctor?action=sort&type=" + type;
            }

            if (action.equals("filter")) {
                String gender = request.getParameter("gender");
                String speciality = request.getParameter("speciality");
                request.setAttribute("gender", gender);
                request.setAttribute("speciality1", speciality);
                if (gender.equals("all") && speciality.equals("all")) {
                    response.sendRedirect("doctor?action=all");
                } else {
                    getdoctor = doctordao.getFilter(speciality, gender);
                }
                url = "doctor?action=filter&gender=" + gender + "&speciality=" + speciality;
            }
            if (action.equals("detail")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Doctor detail = doctordao.getDetail(id);
                int star = doctordao.getStars(detail.getDoctor_id());
                int feedback = doctordao.CountFeedback(detail.getDoctor_id());
                List<RateStar> getRate = doctordao.getRateDoctor(detail.getDoctor_id());
                String allow = request.getRequestURI() + "?" + request.getQueryString();
                if (allow.contains("allow=true")) {
                    allow = "true";
                }
                request.setAttribute("detail", detail);
                request.setAttribute("star", star);
                request.setAttribute("feedback", feedback);
                request.setAttribute("allow", allow);
                request.setAttribute("rate", getRate);
                request.getRequestDispatcher("doctordetail.jsp").forward(request, response);
            }

            if (getdoctor != null) {
                for (Doctor doctor : getdoctor) {
                    int star = doctordao.getStars(doctor.getDoctor_id());
                    int feedback = doctordao.CountFeedback(doctor.getDoctor_id());
                    RateStar rateStar = new RateStar(star, feedback);
                    Account a = new Account(doctor.getAccount().getUsername());
                    Setting s = new Setting(doctor.getSetting().getId(), doctor.getSetting().getName(), doctor.getSetting().getSetting_id(), doctor.getSetting().isStatus());
                    doctorall.add(new Doctor(s, doctor.getDoctor_id(), doctor.getRole_id(),
                            doctor.getDoctor_name(), a, doctor.isGender(), doctor.getDOB(),
                            doctor.getPhone(), doctor.getDescription(), doctor.isStatus(),
                            doctor.getImg(), rateStar, doctor.getFee(), doctor.getPosition()));
                }
                int page, numperpage = 6;
                int size = doctorall.size();
                int num = (size % 6 == 0 ? (size / 6) : ((size / 6)) + 1);
                String xpage = request.getParameter("page");
                if (xpage == null) {
                    page = 1;
                } else {
                    page = Integer.parseInt(xpage);
                }
                int start, end;
                start = (page - 1) * numperpage;
                end = Math.min(page * numperpage, size);
                List<Doctor> doctorlist = doctordao.getListByPage(doctorall, start, end);
                request.setAttribute("page", page);
                request.setAttribute("num", num);
                request.setAttribute("url", url);
                request.setAttribute("speciality", specialitylist);
                request.setAttribute("doctor", doctorlist);
                request.getRequestDispatcher("doctor.jsp").forward(request, response);
            }

// mypatient      
// Câu hỏi: Mục đích của việc hiển thị bảng bệnh nhân là gì 


            
            if (action.equals("mypatient")) {
                int doctor_id = doctordao.getDoctorIDByUsername(user.getUsername());
                // Lấy ra id của bác sĩ Oanh = 19 

                patients = patientdao.getPatientByDoctor(doctor_id);
                // truyền id vào

                request.setAttribute("patients", patients);

                request.getRequestDispatcher("mypatients.jsp").forward(request, response);
            }

            if(action.equals("searchpatient")){
                String search = request.getParameter("search");
                int doctor_id = doctordao.getDoctorIDByUsername(user.getUsername());
                
                 patients = patientdao.search(doctor_id, search);
                
                url = "doctor?action=searchpatient&search=" + search;
                
            }
// mymatient detail   
            if (action.equals("mypatientdetail")) {
                int doctor_id = doctordao.getDoctorIDByUsername(user.getUsername());
                
                int patient_id = Integer.parseInt(request.getParameter("id"));
                
                patients = patientdao.getPatientbyid(patient_id);
                List<model.Appointment> appointmentlist = appointmentdao.getAppointmentByPatient(doctor_id, patient_id);
                
                request.setAttribute("patients", patients);
                request.setAttribute("appointmentlist", appointmentlist);
                
                request.getRequestDispatcher("mypatientdetails.jsp").forward(request, response);
            }

// myappointment
            if (action.equals("myappointment")) {
                List<Appointment> getAppointment = doctordao.getAllAppointment(doctordao.getDoctorIDByUsername(user.getUsername()));
                
                int page, numberpage = 2; // numberpage để lưu số dòng tối đa trong 1 trang 
                int size = getAppointment.size();
                int num = (size % 2 == 0 ? (size / 2) : ((size / 2)) + 1);
                
                String xpage = request.getParameter("page");
                
                if (xpage == null) {
                    page = 1;
                } else {
                    page = Integer.parseInt(xpage);
                }
                
                int start, end;
                start = (page - 1) * numberpage;
                end = Math.min(page * numberpage, size);
                
                List<Appointment> AppointmentList = appointmentdao.getListByPage(getAppointment, start, end);
                
                request.setAttribute("page", page);
                request.setAttribute("num", num);
                request.setAttribute("AppointmentList", AppointmentList);

                request.getRequestDispatcher("myappointment.jsp").forward(request, response);
            }
            
            if(action.equals("search")){
                
            }

        } catch (IOException | SQLException e) {
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
