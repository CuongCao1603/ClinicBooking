<%-- 
    Document   : patientdetail
    Created on : Jul 4, 2023, 8:12:22 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <jsp:include page="../admin//layout/adminhead.jsp"/>
    <body>
        <div class="page-wrapper doctris-theme toggled">
            <jsp:include page="/admin/layout/menu.jsp"/>
            <main class="page-content bg-light">
                <jsp:include page="/admin/layout/adminhead.jsp"/>
                <div class="container-fluid">
                    <div class="layout-specing">
                        <div class="row">
                            <div col-lg-12>
                                <ul class="nav nav-pills nav-justified flex-column flex-sm-row row" id="pills-tab" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link rounded active">
                                            <div class="text-center pt-1 pb-1">
                                                <h4 class="title font-weight-normal mb-0"> Thông tin </h4>
                                            </div>                                           
                                        </a>
                                    </li>

                                    <li class="nav-item">
                                        <a class="nav-link rounded" id="pills-smart-tab" data-bs-toggle="pill" href="#edit" role="tab" aria-controls="edit" aria-selected="false">
                                            <div class="text-center pt-1 pb-1">
                                                <h4 class="title font-weight-normal mb-0">Chỉnh sửa</h4>
                                            </div>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="tab-content" id="pills-tabContent">
                            <div class="tab-pane show active row" id="info" role="tabpanel" aria-labelledby="info">
                                <div class="col-lg-12 col-md-12 mt-4">
                                    <div class="bg-white rounded shadow overflow-hidden">
                                        <div class="p-4 border-bottom">
                                            <h5 class="mb-0"> Thông tin </h5>
                                        </div>
                                        <br><br><br><br><br>
                                        <div class="text-center avatar-profile margin-nagative mt-5 position-relative pb-4 border-bottom">
                                            <c:if test="${patient.account.img == 'default'}">
                                                <img src="../assets//images//avata.png" class="rounded-circle shadow-md avatar avatar-md-md" alt="">
                                            </c:if>
                                            <c:if test="${patient.account.img != 'default'}">
                                                <img src="data:img/png;base64, ${patient.account.img}" class="rounded-circle shadow-md avatar avatar-md-md" alt=""/>>
                                            </c:if>
                                            <h5 class="mb-3 mb-1">${patient.account.name}</h5>
                                            <p class="text-muted mb-0">${patient.account.username}</p>
                                        </div>
                                        <div class="list-unstyled p-4">
                                            <div class="d-flex align-items-center mt-2">
                                                <i class="d-flex align-items-center mt-2"></i>
                                                <h6 class="mb-0">Patient_id</h6>
                                                <p class="text-muted mb-0 ms-2">${patient.patient_id}</p>
                                            </div>

                                            <div class="d-flex align-items-center mt-2">
                                                <i class="uil uil-user align-text-bottom text-primary h5 mb-0 me-2"></i>
                                                <h6 class="mb-0">Giới tính</h6>
                                                <c:if test="${patient.account.gender == true}">
                                                    <p class="text-muted mb-0 ms-2"> Nam </p>
                                                </c:if>
                                                <c:if test="${patient.account.gender == false}">
                                                    <p class="text-muted mb-0 ms-2"> Nam </p>
                                                </c:if>
                                            </div>
                                            <div class="d-flex align-items-center mt-2">
                                                <i class="uil uil-italic align-text-bottom text-primary h5 mb-0 me-2"></i>
                                                <h6 class="mb-0"> Số điện thoại</h6>
                                                <p class="text-muted mb-0 ms-2">${patient.account.phone}</p>
                                            </div>

                                            <div class="d-flex align-items-center mt-2">
                                                <i class="uil uil-italic align-text-bottom text-primary h5 mb-0 me-2"></i>
                                                <h6 class="mb-0">Email</h6>
                                                <p class="text-muted mb-0 ms-2">${patient.account.email}</p>
                                            </div>

                                            <div class="d-flex align-items-center mt-2">
                                                <i class="uil uil-medical-drip align-text-bottom text-primary h5 mb-0 me-2"></i>
                                                <h6 class="mb-0">Ngày sinh</h6>
                                                <p class="text-muted mb-0 ms-2"><fmt:formatDate pattern="dd//MM/yyyy" value="${patient.DOB}"/></p>
                                            </div>
                                            <div class="d-flex align-items-center mt-2">
                                                <i class="uil uil-medical-drip align-text-bottom text-primary h5 mb-0 me-2"></i>
                                                <h6 class="mb-0"> Địa Chỉ </h6>
                                                <P class="text-muted mb-0 me-2" style="padding-left: 10px">${patient.address}</P>
                                            </div>
                                            <div class="d-flex align-items-center mt-2">
                                                <i class="uil uil-medical-drip align-text-bottom text-primary h5 mb-0 me-2"></i>
                                                <h6 class="mb-0">Trạng thái</h6>
                                                <c:if test="${patient.status == true}">
                                                    <p class="text-muted mb-0 ms-2">Hoạt động</p>
                                                </c:if>
                                                <c:if test="${patient.status == false}">
                                                    <p class="text-muted mb-0 ms-2">Khóa</p>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </main>
        </div>






        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/simplebar.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
                <script>

                                            function readURL(input, thumbimage) {
                                                if (input.files && input.files[0]) { //Sử dụng  cho Firefox - chrome
                                                    var reader = new FileReader();
                                                    reader.onload = function (e) {
                                                        $("#thumbimage").attr('src', e.target.result);
                                                    }
                                                    reader.readAsDataURL(input.files[0]);
                                                } else { // Sử dụng cho IE
                                                    $("#thumbimage").attr('src', input.value);

                                                }
                                                $("#thumbimage").show();
                                                $('.filename').text($("#uploadfile").val());
                                                $(".Choicefile").hide();
                                                $(".Update").show();
                                                $(".removeimg").show();
                                            }
                                            $(document).ready(function () {
                                                $(".Choicefile").bind('click', function () {
                                                    $("#uploadfile").click();

                                                });
                                                $(".removeimg").click(function () {
                                                    $("#thumbimage").attr('src', '').hide();
                                                    $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
                                                    $(".removeimg").hide();
                                                    $(".Choicefile").show();
                                                    $(".Update").hide();
                                                    $(".filename").text("");
                                                });
                                            })
        </script>
    </body>
</html>
