<%-- 
    Document   : mypatient
    Created on : Jun 13, 2023, 10:07:54 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
                        <h3 class="mb-0"></h3>
                        <div class="rounded shadow mt-4">
                            <div class="p-4 border-bottom">
                                <h5 class="mb-0"> Bệnh nhân của tôi </h5>
                            </div>
                            <div class="p-4">
                                <jsp:include page="layout/search.jsp"/>
                                <div class="table-responsive bg-white shadow rounded">
                                    <table class="table mb-0 table-center" style="font-size: smaller;">
                                        <thead>
                                            <tr>
                                                <th class="border-bottom" style="min-width: 100px;"> Patient</th>
                                                <th class="border-bottom" style="min-width: 70px;">Phone</th>
                                                <th class="border-bottom" style="min-width: 70px;">Email</th>
                                                <th class="border-bottom" style="min-width: 110px;">Date Booking</th>
                                                <th class="border-bottom" style="min-width: 30px;">DOB</th>
                                                <th class="border-bottom"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${patients}" var="a">
                                                <tr>
                                                    <td>
                                                        <a>
                                                            <div class="d-flex align-items-center">
                                                                <span class="ms-2">${a.account.name}</span>
                                                            </div>
                                                        </a>
                                                    </td>
                                                    <td class="">${a.account.phone}</td>
                                                    <td class="">${a.account.email}</td>
                                                    <td class=""><fmt:formatDate pattern="dd/MM//yyyy" value="${a.appointment.date}"/></td>
                                                    <td class=""><fmt:formatDate pattern="dd/MM/yyyy" value="${a.DOB}"/></td>
                                                    <td class="text-end">
                                                        <a href="doctor?acction=detailpatient&id=${a.patient_id}"><u>Detail</u></a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>
        <a href="#" onclick="topFunction()" id="back-to-top" clas="btn btn-icon btn-pills btn-primary back-to-top"><i data-feather="arrow-up" class="icons"></i> </a>
        
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
    </body>
</html>
