/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.PatientDao;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Patient;

/**
 *
 * @author Trung
 */
public class PatientManage extends HttpServlet {

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
        PatientDao patientdao = new PatientDao();
        List<Patient> patientlist = null;
        String url = null;
        String alert = null;
        String message = null;
        String action = request.getParameter("action");

        try {
            if (action.equals("all")) {
                url = "patientmanage?action=all";
                patientlist = patientdao.getAllPatient();
            }
            
            if (action.equals("search")) {
                String search = request.getParameter("search");
                url = "patientmanage?action=search&search="+search;
                patientlist = patientdao.getPatientByName(search);
            }
         
            if (action.equals("detail")) {
                String username = request.getParameter("username");
                Patient patient = new Patient();
                patient = patientdao.getPatientByUsername(username);
                request.setAttribute("patient", patient);
                request.getRequestDispatcher("admin/patientdetail.jsp").forward(request, response);
            }

            if (action.equals("update_patient")) {
                int patient_id = Integer.parseInt(request.getParameter("patient_id"));
                int phone = Integer.parseInt(request.getParameter("phone"));
                String username = request.getParameter("username");
                String name = request.getParameter("name");
                String adress = request.getParameter("address");
                boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
                boolean status = Boolean.parseBoolean(request.getParameter("status"));
                Date dob = Date.valueOf(request.getParameter("DOB"));
                patientdao.PatientUpdate(username, adress, dob, patient_id, name, status, gender, phone);
                alert = "success";
                message = "Cập nhật thôn tin thành công";
                request.setAttribute("alert", alert);
                request.setAttribute("message", message);
                request.getRequestDispatcher("patientmanage?action=detail&username=" + username).forward(request, response);
            }
            
            if (patientlist != null) {
                int page, numperpage = 8;
                int size = patientlist.size();
                int num = (size % 8 == 0 ? (size / 8) : ((size / 8)) + 1);//so trang
                String xpage = request.getParameter("page");
                if (xpage == null) {
                    page = 1;
                } else {
                    page = Integer.parseInt(xpage);
                }
                int start, end;
                start = (page - 1) * numperpage;
                end = Math.min(page * numperpage, size);
                List<Patient> patientDetails = patientdao.getListByPage(patientlist, start, end);
                request.setAttribute("page", page);
                request.setAttribute("num", num);
                request.setAttribute("url", url);
                request.setAttribute("patientlist", patientlist);
                request.setAttribute("patientDetails", patientDetails);
                request.getRequestDispatcher("admin/patient.jsp").forward(request, response);
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
