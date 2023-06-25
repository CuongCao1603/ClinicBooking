<%-- 
    Document   : mkt_blogdetail
    Created on : Feb 23, 2022, 2:51:36 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

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
                            <div class="card border-0 shadow overflow-hidden">
                                <div class="tab-content p-4" id="pills-tabContent">
                                    <label class="form-label">Ảnh<span class="text-danger">*</span></label>
                                    <br>
                                    <c:if test="${ not empty blog.img}">
                                        <img src="data:image/jpg;base64,${blog.img}" id="output" width="200" />
                                    </c:if>
                                    <form action="blogmanage?action=update_image&blog_id=${blog.blog_id}" method="POST" enctype="multipart/form-data" onSubmit="document.getElementById('submit').disabled = true;">
                                        <div>
                                            <div id="myfileupload">
                                                <input type="file" name="image" id="uploadfile" name="ImageUpload" onchange="readURL(this);" />
                                            </div>
                                            <div id="thumbbox">
                                                <img class="rounded" height="20%" width="30%" alt="Thumb image" id="thumbimage" style="display: none" />
                                                <a class="removeimg" href="javascript:"></a>
                                            </div>
                                            <div id="boxchoice">
                                                <a href="javascript:" class="Choicefile"><i class="fas fa-cloud-upload-alt"></i> Chọn ảnh</a>
                                                <p style="clear:both"></p>
                                                <input type="submit" id="submit" style="display: none" name="send" class="Update btn btn-primary"
                                                       value="Cập nhật">
                                                <p style="clear:both"></p>
                                            </div> 
                                        </div>
                                    </form>
                                    <br>
                                    <form action="blogmanage?action=update&blog_id=${blog.blog_id}" method="POST" class="mt-4" onSubmit="document.getElementById('submit').disabled = true;" enctype="multipart/form-data">

                                        <div class="row">
                                            <input type="hidden" name="blogid" value="${blog.blog_id}" /> <br/>
                                            <div class="col-lg-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Tác giả <span class="text-danger"></span></label>
                                                    <input name="author" id="name" type="text" class="form-control" value="${blog.author}" readonly="">
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Tiêu đề <span class="text-danger">* chỉnh sửa</span></label>
                                                    <input name="title" id="name" type="text" class="form-control" value="${blog.title}">
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Danh Mục<span class="text-danger">*</span></label>
                                                    <select name="category_id" class="form-select" aria-label="Default select example">
                                                        <c:forEach items="${categories}" var="c">
                                                            <option value="${c.id}">${c.name}</option>
                                                        </c:forEach>
                                                    </select> 
                                                </div>
                                            </div>
                                                
                                            <div class="mb-3">
                                                <label class="form-label">Thông Tin Tóm Tắt<span class="text-danger">*</span></label>
                                                <textarea rows="" cols="" id="brief" name="brief" style="width: 720px; height:50px" >${blog.brief}</textarea>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Mô tả<span class="text-danger">*</span></label>
<!--                                                <input name="describe" id="name" type="text" class="form-control" value="${blog.describe}">-->
                                                <textarea rows="" cols="" id="describe" name="describe" style="width: 820px; height:175px" >${blog.describe}</textarea>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Nổi bật <span class="text-danger"></span></label>
                                                <table>
                                                    <tbody>
                                                        <tr>
                                                            <td><input id="credit" name="featured" ${blog.featured==true?"checked":""}
                                                                       value="true" type="radio" class="form-check-input"
                                                                       checked required ></td>
                                                            <td><label class="form-check-label">Nổi bật</label></td>
                                                            <td></td>
                                                            <td><input id="debit" name="featured" ${blog.featured==false?"checked":""}
                                                                       value="false" type="radio" class="form-check-input"
                                                                       required></td>
                                                            <td><label class="form-check-label">Không nổi bật</label></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label">Status <span class="text-danger"></span></label>
                                                <table>
                                                    <tbody>
                                                        <tr>
                                                            <td><input id="credit" name="status" ${blog.status==true?"checked":""}
                                                                       value="true" type="radio" class="form-check-input"
                                                                       checked required ></td>
                                                            <td><label class="form-check-label">Active</label></td>
                                                            <td></td>
                                                            <td><input id="debit" name="status" ${blog.status==false?"checked":""}
                                                                       value="false" type="radio" class="form-check-input"
                                                                       required></td>
                                                            <td><label class="form-check-label">Disable</label></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>                                                 
                                        </div>

                                        <div class="row">
                                            <div class="col-sm-12">
                                                <input type="submit" id="submit" name="send" class="btn btn-primary"
                                                       value="Cập nhật">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

                <jsp:include page="../admin/layout/footer.jsp"/>
            </main>
        </div>

        <style>
            .Choicefile{
                display: block;
                background: #396CF0;
                border: 1px solid #fff;
                color: #fff;
                width: 150px;
                text-align: center;
                text-decoration: none;
                cursor: pointer;
                padding: 5px 0px;
                border-radius: 5px;
                font-weight: 500;
                align-items: center;
                justify-content: center;
            }

            .Choicefile:hover {
                text-decoration: none;
                color: white;
            }

            #uploadfile,
            .removeimg {
                display: none;
            }

            #thumbbox {
                position: relative;
                width: 100%;
                margin-bottom: 20px;
            }

            .removeimg {
                height: 25px;
                position: absolute;
                background-repeat: no-repeat;
                top: 5px;
                left: 5px;
                background-size: 25px;
                width: 25px;
                border-radius: 50%;

            }

            .removeimg::before {
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                content: '';
                border: 1px solid red;
                background: red;
                text-align: center;
                display: block;
                margin-top: 11px;
                transform: rotate(45deg);
            }

            .removeimg::after {
                content: '';
                background: red;
                border: 1px solid red;
                text-align: center;
                display: block;
                transform: rotate(-45deg);
                margin-top: -2px;
            }
        </style>
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
                                                        var editor = '';
                                                        $(document).ready(function () {
                                                            editor = CKEDITOR.replace('brief');
                                                            editor = CKEDITOR.replace('describe');
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

