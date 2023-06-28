<%-- 
    Document   : setting
    Created on : Jan 14, 2022, 2:58:38 PM
    Author     : Khuong Hung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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
                            <div class="col-xl-5 col-lg-6 col-md-2">
                                <h5 class="mb-0">Settings</h5>
                                <h6>${requestScope.success}</h6>
                            </div>
                            <div class="col-xl-4 col-lg-6 col-md-2">
                                <div class="search-bar p-0 d-none d-lg-block ms-2">
                                    <div id="search" class="menu-search mb-0">
                                        <form action="setting?action=search" method="POST" id="searchform" class="searchform">
                                            <div>
                                                <input type="text" class="form-control border rounded-pill" name="search" id="s" placeholder="Tìm kiếm setting...">
                                                <input type="submit" id="searchsubmit" value="Search">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-lg-6 col-md-8 mt-4 mt-md-0">
                                <div class="justify-content-md-end">
                                    <form>
                                        <div class="row justify-content-between align-items-center">
                                            <div class="col-sm-12 col-md-5">
                                                <div class="mb-0 position-relative">
                                                    <div class="dropdown">
                                                        <button style="color: #000; background-color: #215AEE ;border:none; font-family: sans-serif; " class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                                            Type
                                                        </button>
                                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                                            <li><a class="dropdown-item" href="setting?action=all">Tất cả</a></li>
                                                                <c:forEach items="${setting}" var="s">
                                                                <li><a class="dropdown-item" href="setting?action=${s.setting_name}">${s.setting_name}</a></li>
                                                                </c:forEach>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-12 col-md-7 mt-4 mt-sm-0">
                                                <div class="d-grid">
                                                    <a href="#" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addnew">Thêm mới</a>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 mt-4">
                                <div class="table-responsive bg-white shadow rounded">
                                    <table class="table mb-0 table-center">
                                        <thead>
                                            <tr>
                                                <th class="border-bottom p-3" >Setting ID</th>
                                                <th class="border-bottom p-3" >Type</th>
                                                <th class="border-bottom p-3" >Value</th>
                                                <th class="border-bottom p-3" >Order</th>
                                                <th class="border-bottom p-3" >Note</th>
                                                <th class="border-bottom p-3" >Status</th>
                                                <th class="border-bottom p-3" ></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${settingdetails}" var="sd">
                                                <tr>
                                                    <th class="p-3">${sd.setting_id}</th>
                                                        <c:forEach items="${setting}" var="s">
                                                            <c:if test="${sd.setting_id == s.setting_id}">
                                                            <td class="p-3">${s.setting_name}</td>
                                                        </c:if>
                                                    </c:forEach>
                                                    <td class="p-3">${sd.name}</td>
                                                    <td class="p-3">${sd.order}</td>
                                                    <td class="p-3">${sd.note}</td>
                                                    <c:if test="${sd.status == true}">
                                                        <td class="p-3">Active</td>
                                                    </c:if>
                                                    <c:if test="${sd.status == false}">
                                                        <td class="p-3">Disable</td>
                                                    </c:if>
                                                    <td class="text-end p-3">
                                                        <a href="#" type="button"class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#edit${sd.id}${sd.setting_id}">Chỉnh sửa</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                </div>
                            </div>
                        </div>
                        <c:set var="page" value="${page}"/>
                        <div class="row text-center">
                            <div class="col-12 mt-4">
                                <div class="d-md-flex align-items-center text-center justify-content-between">
                                    <ul class="pagination justify-content-center mb-0 mt-3 mt-sm-0">

                                        <c:forEach begin="${1}" end="${num}" var="i">
                                            <li class="page-item ${i==page?"active":""}"><a class="page-link" href="${url}&page=${i}">${i}</a></li>
                                            </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:forEach items="${settingdetails}" var="sd">
                        <div class="modal fade" id="edit${sd.id}${sd.setting_id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header border-bottom p-3">
                                        <h5 class="modal-title" id="exampleModalLabel"></h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body p-3 pt-4">
                                        <form action="setting?action=update" method="POST">
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="mb-3">
                                                        <label class="form-label">Setting ID <span class="text-danger">*</span></label>
                                                        <input value="${sd.setting_id}" readonly name="setting_id" id="name" type="text" class="form-control">
                                                    </div>
                                                    <div class="mb-3">
                                                        <input value="${sd.id}" hidden name="id" id="name" type="text" class="form-control">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label">Value <span class="text-danger">*</span></label>
                                                        <input value="${sd.name}" name="value" id="name" type="text" class="form-control">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label">Order <span class="text-danger">*</span></label>
                                                        <input value="${sd.order}" name="order" id="name" type="text" class="form-control">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label">Note <span class="text-danger">*</span></label>
                                                        <input value="${sd.note}" name="note" id="name" type="text" class="form-control">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label">Status <span class="text-danger"></span></label>
                                                        <table>
                                                            <tbody>
                                                                <tr>
                                                                    <td><input id="credit" name="status" ${sd.status==true?"checked":""} value="true" type="radio" class="form-check-input"
                                                                               checked required ></td>
                                                                    <td><label class="form-check-label">Active</label></td>
                                                                    <td></td>
                                                                    <td><input id="debit" name="status" ${sd.status==false?"checked":""} value="false" type="radio" class="form-check-input"
                                                                               required></td>
                                                                    <td><label class="form-check-label">Disable</label></td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <div class="col-lg-12">
                                                    <div class="d-grid">
                                                        <button type="submit" class="btn btn-primary">Chỉnh sửa</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <jsp:include page="../admin/layout/footer.jsp"/>
                </div>
            </main>
        </div>

        <div class="modal fade" id="addnew" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header border-bottom p-3">
                        <h5 class="modal-title" id="exampleModalLabel">Thêm mới setting</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body p-3 pt-4">
                        <form action="setting?action=addnew" method="POST">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="mb-3">
                                        <label class="form-label">Type <span class="text-danger">*</span></label>
                                        <select name="setting_id" class="form-select" aria-label="Default select example">
                                            <c:forEach items="${setting}" var="s">
                                                <option value="${s.setting_id}">${s.setting_name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Value <span class="text-danger">*</span></label>
                                    <input name="value" id="name" type="text" class="form-control">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Order <span class="text-danger">*</span></label>
                                    <input name="order" id="name" type="text" class="form-control">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Note <span class="text-danger">*</span></label>
                                    <input name="note" id="name" type="text" class="form-control">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Status <span class="text-danger"></span></label>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td><input id="credit" name="status" value="true" type="radio" class="form-check-input"
                                                           checked required ></td>
                                                <td><label class="form-check-label">Active</label></td>
                                                <td></td>
                                                <td><input id="debit" name="status" value="false" type="radio" class="form-check-input"
                                                           required></td>
                                                <td><label class="form-check-label">Disable</label></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary">Thêm</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
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