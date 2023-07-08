<%-- 
    Document   : patient
    Created on : Jul 4, 2023, 8:12:10 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <jsp:include page="../admin/layout/adminhead.jsp"/>
    <body>
        <div class="page-wrapper doctris-theme toggled">
            <jsp:include page="../admin/layout/menu.jsp"/>
            
            <main class="page-content bg-light">
                <jsp:include page="../admin/layout/headmenu.jsp"/>
                <div class="container-fluid">
                    <div class="layout-specing">

                        <div class="row">
                            <div class="col-md-11 row">
                                <div class="col-md-4">
                                    <h5 class="mb-0">Patients</h5>
                                    <h6></h6>
                                </div>
                                <div class="col-md-7">
                                    <div class="search-bar p-0 d-lg-block ms-2">
                                        <div id="search" class="menu-search mb-0">
                                            <form action="patientmanage?action=search" method="POST" id="searchform" class="searchform">
                                                <div>
                                                    <input type="text" class="form-control border rounded-pill" name="search" id="s" placeholder="Tìm kiếm bệnh nhân...">
                                                    <input type="submit" id="searchsubmit" value="Search">
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-1">
                                <div class="justify-content-md-end row">
                                    <div class="d-grid">
                                        <a href="#" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#filter">Lọc</a>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-12 mt-4">
                                <div class="table-responsive bg-white shadow rounded">
                                    <table class="table mb-0 table-center">
                                        <thead>
                                            <tr>
                                                <th class="border-bottom p-3">ID</th>
                                                <th class="border-bottom p-3">Họ Tên</th>
                                                <th class="border-bottom p-3">Giới tính</th>
                                                <th class="border-bottom p-3">Ngày sinh</th>
                                                <th class="border-bottom p-3">Trạng Thái</th>
                                                <th class="border-bottom p-3"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <c:forEach items="${patientlist}" var="a" >
                                                    <td class="p-3">${a.patient_id}</td>
                                                    <td class="p-3">${a.account.name}</td>

                                                    <c:if test="${a.account.gender == true}">
                                                        <td class="p-3">Nam</td>
                                                    </c:if>
                                                    <c:if test="${a.account.gender == false}">
                                                        <td class="p-3">Nữ</td>
                                                    </c:if>
                                                    <td class="p-3"><fmt:formatDate pattern="dd/MM/yyyy" value="${a.DOB}" /></td>
                                                    <c:if test="${a.status == false}" >
                                                        <td class="p-3">Khóa</td>
                                                    </c:if>
                                                    <c:if test="${a.status == true}" >
                                                        <td class="p-3">Hoạt động</td>
                                                    </c:if>
                                                    <td class="text-end p-3">
                                                        <a href="patientmanage?action=detail&username=${a.account.username}" type="button" class="btn btn-info">
                                                            Chi tiết
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <c:set var="page" value="${page}" />
                            <div class="row text-center">
                                <div class="col-12 mt-4">
                                    <div class="d-md-flex align-items-lg-center text-center justify-content-between">
                                        <ul class="pagination justify-content-center mb-0 mt-3 mt-sm-0">
                                            <c:forEach begin="${1}" end="${num}" var="i">
                                                <li class="page-item ${i==page?"active":""}"><a class="page-link" href="${url}&page=${i}">${i}</a></li>
                                                </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!--some-->
                </div>
                <jsp:include page="../admin/layout/footer.jsp"/>
            </main>
        </div>

        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/simplebar.min.js"></script>
        <script src="assets/js/select2.min.js"></script>
        <script src="assets/js/select2.init.js"></script>
        <script src="assets/js/flatpickr.min.js"></script>
        <script src="assets/js/flatpickr.init.js"></script>
        <script src="assets/js/jquery.timepicker.min.js"></script> 
        <script src="assets/js/timepicker.init.js"></script> 
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>

    </body>
</html>
