<%-- 
    Document   : patient
    Created on : Jul 4, 2023, 8:12:10 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="../admin/layout/adminhead.jsp"/>
    <body>
        <div calss="">
            <jsp:include page="../admin/layout/menu.jsp"/>
            <main class="page-content bg-light">
                <jsp:include page="../admin/layout/headmenu.jsp"/>
                <div class="container-fluid">
                    <div class="layout-specing">
                        <div class="row">
                            <div class="col-md-11 row">
                                <div class="col-md-4">
                                    <<h5 class="mb-0">Patients</h5>
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
                                <div class="col-md-1">
                                    
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
                                                <c:forEach items="${patientD}"
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>
