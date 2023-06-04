/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import config.EncodeData;
import config.Validate;

/**
 *
 * @author doans
 */
public class UserController extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        UserDAO userdao = new UserDAO();
        Account user = (Account) session.getAttribute("user");
        String alert = null;
        String message = null;

        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (action.equals("login")) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            if (action.equals("checklogin")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String remember = request.getParameter("remember");
                String enpassword = EncodeData.enCode(password);
                Account account = userdao.login(email, enpassword);
                if (account == null) {
                    request.setAttribute("error", "Email hoặc mật khẩu không chính xác!");
                    request.getRequestDispatcher("user?action=login").forward(request, response);
                } else if (account.isStatus() == false) {
                    request.setAttribute("error", "Tài khoản đã bị khóa !");
                    request.getRequestDispatcher("user?action=login").forward(request, response);
                } else {
                    session.setAttribute("user", account);
                    Cookie cemail = new Cookie("email", email);
                    Cookie cpass = new Cookie("pass", password);
                    Cookie rem = new Cookie("remember", remember);
                    if (remember != null) {
                        cemail.setMaxAge(60 * 60 * 24 * 30);
                        cpass.setMaxAge(60 * 60 * 24 * 3);
                        rem.setMaxAge(60 * 60 * 24 * 30);
                    } else {
                        cemail.setMaxAge(0);
                        cpass.setMaxAge(0);
                        rem.setMaxAge(0);
                    }
                    response.addCookie(cemail);
                    response.addCookie(cpass);
                    response.addCookie(rem);
                    if (account.getRole().getRole_id() == 1) {
                        response.sendRedirect("dashboard?action=default");
                    } else {
                        response.sendRedirect("home");
                    }
                }
            }

            if (action.equals("profile")) {
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            }

            if (action.equals("logout")) {
                session.invalidate();
                response.sendRedirect("home");
                return;
            }

            if (action.equals("changepassword")) {
                String oldpassword = EncodeData.enCode(request.getParameter("oldpassword"));
                String newpassword = request.getParameter("newpassword");
                String renewpassword = request.getParameter("renewpassword");
                if (!oldpassword.equals(user.getPassword())) {
                    request.setAttribute("oldpassword", EncodeData.deCode(oldpassword));
                    request.setAttribute("newpassword", newpassword);
                    request.setAttribute("renewpassword", renewpassword);
                    request.setAttribute("passerror", "Mật khẩu cũ không đúng!");
                    request.getRequestDispatcher("user?action=profile").forward(request, response);
                } else {
                    newpassword = EncodeData.enCode(newpassword);
                    userdao.Recover(user.getUsername(), newpassword);
                    request.setAttribute("success", "Thay đổi mật khẩu thành công, mời bạn đăng nhập lại!");
                    request.getRequestDispatcher("user?action=login").forward(request, response);
                }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
