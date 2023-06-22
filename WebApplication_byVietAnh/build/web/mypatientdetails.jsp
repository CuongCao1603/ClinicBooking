<%-- 
    Document   : mypatientdetails
    Created on : Jun 22, 2023, 8:41:56 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <jsp:include page="layout/head.jsp"/>
    <body> 
        <jsp:include page="layout/menu_white.jsp"/>
        <section class="bg-dashboard">
            <div class="container">
                <div class="row justify-content-center">
                    <jsp:include page="layout/profileMenu.jsp"/>
                    <div class="col-xl-8 col-lg-8 col-md-7 mt-4 pt-2 mt-sm-0 pt-sm-0">
                        <div class="col-lg-12 col-md-12 mt-4">
                            <div class="bg-white rounded shadow overflow-hidden">
                                <div class="p-4 border-bottom">
                                    <h5 class="mb-0"> Thông tin chi tiết bệnh nhân</h5>
                                </div>

                                <div class="p-4">
                                    <div class="d-flex align-items-center mt-2">
                                        <i class="uil uil-italic align-text-bottom text-primary h5 mb-0 me-2"></i>
                                        <h6 class="mb-0"></h6>
                                        <p class="text-muted mb-0 ms-2"></p>
                                    </div>
                                    <div>
                                        <i></i>
                                        <h6></h6>
                                        <p></p>
                                    </div>
                                    <div>
                                        <i></i>
                                        <h6></h6>
                                        <p></p>
                                    </div>
                                    <div>
                                        <i></i>
                                        <h6></h6>
                                        <p></p>
                                    </div>
                                    <div>
                                        <i></i>
                                        <h6></h6>
                                        <p></p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-12 col-md-12 mt-4">
                            <div class="bg-white rounded shadow overflow-hidden">
                                <div class="p-4 border-bottom">
                                    <h5 class="mb-0">Danh sách cuộc hẹn bệnh nhân</h5>
                                </div>
                                <table class="table p-4 mb-0 table center">
                                    <thead>
                                        <tr>
                                            <th class="border-bottom p-3">Ngày</th>
                                            <th class="border-bottom p-3">Thời gian</th>
                                            <th class="border-bottom p-3">Trạng Thái</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${appointmentlist}" var="a">
                                            <tr>
                                                <td class="p-3"></td>
                                                <td class="p-3"></td>
                                                <td class="p-3"></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <jsp:include page="layout/footer.jsp"/>
        <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top"><i data-feather="arrow-up" class="icons"></i></a>
            <jsp:include page="layout/search.jsp"/>
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script
</body>
                        </html>
