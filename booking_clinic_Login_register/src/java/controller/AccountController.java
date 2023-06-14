/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
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
import config.*;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import model.Role;

/**
 *
 * @author doans
 */
public class AccountController extends HttpServlet {

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
        
        String action = request.getParameter("action"); // lấy giá trị ở action bên jsp lưu vào biến action 
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        
        AccountDAO userdao = new AccountDAO();
        String alert = null;
        String message = null;

        try {
            
//Login
            if (action.equals("login")) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            if (action.equals("checklogin")) {
                
                String email = request.getParameter("email"); // Lấy giá trị ở ô input có name="email" - lưu vào biến email
                String password = request.getParameter("password");
                String remember = request.getParameter("remember");
                
                String enpassword = EncodeData.enCode(password); // mã hóa
                
                Account account = userdao.login(email, enpassword);
                
                if (account == null) {
                    request.setAttribute("error", "Email hoặc mật khẩu không chính xác!");
                    request.getRequestDispatcher("user?action=login").forward(request, response);
                }
                else {
                    //sessin https://www.youtube.com/watch?v=WJB5V0chJiM
                    session.setAttribute("user", account);
                    // Lưu account vào session có tên là user
                    // Vì lưu vào session mình đóng tab lại là mất ngay
                    // Nên dùng cookies lưu vào thì mình kiểm soát đc thời gian 
                    Cookie cemail = new Cookie("email", email);
                    Cookie cpass = new Cookie("pass", password);
                    Cookie rem = new Cookie("remember", remember);
                    
                    // Tại sao có session rồi mà còn phải set cookies
                    if (remember != null) {
                        cemail.setMaxAge(60 * 60 * 24 * 30); // tính bằng giây 
                        cpass.setMaxAge(60 * 60 * 24 * 3); // lưu mật khảu 3 ngày 
                        rem.setMaxAge(60 * 60 * 24 * 30); // tại sao lại phải set cookies cho remember
                    } else {
                        //cookie.setMaxAge(-1);  
                        //-1 represents that the given cookie exists until the browser is shut down. 
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
            
// Log out
            if (action.equals("logout")) {
                session.invalidate();
                response.sendRedirect("home");
                return;
            }
            
// Register
            if (action.equals("register")) {
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            if (action.equals("checkregister")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String repassword = request.getParameter("repassword");
                String username = request.getParameter("username");
                String name = request.getParameter("name");
                String rgender = request.getParameter("gender"); // raw gender
                String rphone = request.getParameter("phone");   // raw phone 
                
                int role_id = 2;
                String img = "default";
                boolean status = true;
                String enpassword = EncodeData.enCode(password);
                boolean gender = Boolean.parseBoolean(rgender);
                int phone = Integer.parseInt(rphone);
                String fullname = Validate.capitalizeFirstLetter(name);
                
                Account account = userdao.checkAcc(email, username);
                
                if (account != null) {
                    
                    request.setAttribute("email", email);
                    request.setAttribute("password", password);
                    request.setAttribute("repassword", repassword);
                    request.setAttribute("username", username);
                    request.setAttribute("name", name);
                    request.setAttribute("gender", rgender.equals("true"));
                    request.setAttribute("phone", rphone);
                    
                    request.setAttribute("error", "Email hoặc username đã tồn tại trên hệ thống!");
                    request.getRequestDispatcher("user?action=register").forward(request, response);
                } else {
                    Role r = new Role(role_id);
                    userdao.Register(email, EncodeData.enCode(password), username, role_id, name, phone, gender, status);
                    
                    Account a = new Account(username, r, EncodeData.enCode(enpassword), fullname, gender, phone, email, img, status);
                    session.setAttribute("register", a);
                    
                    request.getRequestDispatcher("user?action=login").forward(request, response);
                }
            }
//// Generacaptcha
//            if (action.equals("generalcaptcha")) {
//                String captcha = Captcha.getCaptcha(16);
//                Account a = (Account) session.getAttribute("register");
//                String email = a.getEmail();
//                String password = a.getPassword();
//                String username = a.getUsername();
//                String name = a.getName();
//                int phone = a.getPhone();
//                boolean gender = a.isGender();
//                int role_id = a.getRole().getRole_id();
//                boolean status = a.isStatus();
//                String content = "&email=" + email + "&password=" + password
//                        + "&username=" + username + "&role_id=" + role_id + ""
//                        + "&name=" + name + "&gender=" + gender + "&status=" + status + "&phone=" + phone + "&captcha=" + captcha + "&type=register";
//                String enContent = "https://doctriscare.ml/user?action=verification&id=" + EncodeData.enCode(content);
//                SendMail.setContent(username, enContent, email);
//                userdao.AddCaptcha(username, captcha);
//                request.setAttribute("error", "Link xác thực đã được gửi đến bạn");
//                request.getRequestDispatcher("user?action=login").forward(request, response);
//            }
// Profile
            if (action.equals("profile")) {
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            }

            if (action.equals("update_image")) {
                String username = user.getUsername();
                Part image = request.getPart("image");
                if (image != null) {
                    try {
                        Account acc = userdao.getAccountByUsername(username);
                        userdao.UpdateImage(username, image);
                        session.setAttribute("user", acc);
                    } catch (Exception e) {
                    }
                }
                alert = "success";
                message = "Cập nhật ảnh thành công";
                request.setAttribute("alert", alert);
                request.setAttribute("message", message);
                request.getRequestDispatcher("user?action=profile").forward(request, response);
            }

            if (action.equals("updateprofile")) {
                String username = request.getParameter("username");
                String name = request.getParameter("name");
                int phone = Integer.parseInt(request.getParameter("phone"));
                boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
                userdao.UpdateProfile(username, name, phone, gender);
                Account a = new Account(username, user.getRole(), name, gender, phone, user.getEmail(), user.getImg(), user.isStatus());
                session.setAttribute("user", a);
                request.setAttribute("updatesuccess", "Thông tin đã được cập nhật!");
                response.sendRedirect("user?action=profile");
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
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
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
