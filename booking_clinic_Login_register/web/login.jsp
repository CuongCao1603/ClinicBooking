<%-- 
    Document   : login




Thứ tự chạy hàm 
Login.jsp -> UserController.java -> UserDAO (-> UserController.java) -> Database -> UserDAO -> UserController -> home 

Thứ tự viết hàm 
viết form login.jsp
Dùng UserController.java để lấy dữ liệu từ trang login.jsp
Mã hóa mật khẩu để khớp với chuỗi trong database 
Dùng UserDAO để check tài khoản tồn tại hay ko -> trả về giá trị cho UserController
Sau khi nhận giá trị trả vể, UserController check tài khoản
-> nếu ko tồn tại thì quay lại trang login 
-> nếu tồn tại thì chuyển đến trang home 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <jsp:include page="layout/head.jsp"/>

    <body>
        <div class="back-to-home rounded d-none d-sm-block">
            <a href="index.jsp" class="btn btn-icon btn-primary"><i data-feather="home" class="icons"></i></a>
        </div>

        <!-- Hero Start -->
        <section class="bg-home d-flex bg-light align-items-center">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 col-md-8">
                        <div class="card login-page bg-white shadow mt-4 rounded border-0">
                            <div class="card-body">
                                <h4 class="text-center">Đăng nhập</h4> 
                                <c:set var="cookie" value="${pageContext.request.cookies}"/>
<!--Trong công nghệ JSP (JavaServer Pages), pageContext là một đối tượng ẩn cung cấp quyền truy cập vào các đối tượng khác nhau 
được liên kết với yêu cầu hiện tại, chẳng hạn như request, response, session, servlet context, and so on
Trong mã bạn đã cung cấp, pageContext.request đề cập đến đối tượng HttpServletRequest được liên kết với yêu cầu hiện tại. 
Thuộc tính cookie của đối tượng này trả về một mảng các đối tượng Cookie đại diện cho tất cả các cookie có trong yêu cầu.
-->
    
                                <form action="user?action=checklogin" method="POST" class="login-form mt-4">
                                    <p style="color: red; align-content: center;">
                                        ${requestScope.error}
                                    </p>
                                    <p style="color: blue; align-content: center;">
                                        ${requestScope.success}
                                    </p>
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="mb-3">
                                                
                                                <label class="form-label">Email <span class="text-danger">*</span></label>
                                                <input type="email" oninvalid="CheckEmail(this);" oninput="CheckEmail(this);" value="${cookie.email.value}" class="form-control" placeholder="Email" name="email" required="">
                                            <%-- 
                                            
                                            oninvalid - nếu để chống thanh điền thì sẽ gọi đến hàm CheckEmail() // pattern=".{6,}" -> Must contain 6 or more characters
                                            Thuộc tính oninput kích hoạt khi giá trị của thẻ <input> và <textarea> thay đổi.
                                            oninvalid : https://www.w3schools.com/jsref/event_oninvalid.asp#:~:text=The%20oninvalid%20event%20occurs%20when%20a%20submittable%20%3Cinput%3E,must%20be%20filled%20out%20before%20submitting%20the%20form%29.
                                            oninput : https://www.w3schools.com/jsref/event_oninput.asp
                                            --%>
                                            
                                            </div>
                                        </div>
                                            
                                        <div class="col-lg-12">
                                            <div class="mb-3">
                                                
                                                <label class="form-label">Mật khẩu <span class="text-danger">*</span></label>
                                                <input type="password" oninvalid="CheckPassword(this);" oninput="CheckPassword(this);" value="${cookie.pass.value}" class="form-control" name="password" placeholder="Password" required="">
                                        <!--    required="" phải điền vào các trường 
                                                Thuộc tính value để lưu cookies trên thanh điền và ko phải nhập lại -->
                                                
                                            </div>
                                        </div>

                                        <div class="col-lg-12">
                                            <div class="d-flex justify-content-between">
                                                
                                                <div class="mb-3">
                                                    <div class="form-check">
                                                        <input ${(cookie.rem.value eq 'ON')?"checked":""} class="form-check-input align-middle" type="checkbox" name="remember" id="remember-check">
                                                        <!--so sánh rem xem có 'ON' hay ko , 
                                                        Nếu ko on thì cút 
                                                        Nếu có on thì checked -> 
                                                        -->
                                                        <label class="form-check-label" for="remember-check">Lưu tài khoản</label>
<!--                                                        label liên kết với input qua for="remember-check"-->
                                                        <!--Giá trị của thuộc tính "for" phải bằng thuộc tính "id" của điều khiển biểu mẫu được liên kết với nó.
                                                        The label được liên kết với"checkbox" bằng thuộc tính "for", tham chiếu đến "id" của "checkbox".
                                                        -->
                                                        
                                                    </div>
                                                </div>
                                                        
                                                <a href="user?action=recover" class="text-dark h6 mb-0">Quên mật khẩu ?</a>
                                            </div>
                                        </div>
                                        <div class="col-lg-12 mb-0">
                                            <div class="d-grid">
                                                <button class="btn btn-primary" id="submit">Đăng nhập</button>
                                            </div>
                                        </div>
                                        <div class="col-12 text-center">
                                            <p class="mb-0 mt-3"><small class="text-dark me-2">Chưa có tài khoản ?</small> <a href="user?action=register" class="text-dark fw-bold">Đăng ký</a></p>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
        </section>

        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>

    </body>

</html>
