/* Template Name: Doctris - Doctor Appointment Booking System
   Author: Shreethemes
   Website: https://shreethemes.in/
   Mail: support@shreethemes.in
   Version: 1.0.0
   Updated: July 2021
   File Description: Main JS file of the template
*/

/*********************************/
/*         INDEX                 */
/*================================
 *     01.  Loader               *
 *     02.  Toggle Menus         *
 *     03.  Active Menu          *
 *     04.  Clickable Menu       *
 *     05.  Back to top          *
 *     06.  Feather icon         *
 *     06.  DD Menu              *
 *     06.  Active Sidebar Menu  *
 ================================*/


window.onload = function loader() {
    // Preloader
    setTimeout(() => {
        document.getElementById('preloader').style.visibility = 'hidden';
        document.getElementById('preloader').style.opacity = '0';
    }, 50);

    // Menus
    activateMenu();
    activateSidebarMenu();
}

$('th').click(function(){
    var table = $(this).parents('table').eq(0)
    var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()))
    this.asc = !this.asc
    if (!this.asc){rows = rows.reverse()}
    for (var i = 0; i < rows.length; i++){table.append(rows[i])}
})
function comparer(index) {
    return function(a, b) {
        var valA = getCellValue(a, index), valB = getCellValue(b, index)
        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB)
    }
}
function getCellValue(row, index){ return $(row).children('td').eq(index).text() }

//Menu
// Toggle menu
function toggleMenu() {
    document.getElementById('isToggle').classList.toggle('open');
    var isOpen = document.getElementById('navigation')
    if (isOpen.style.display === "block") {
        isOpen.style.display = "none";
    } else {
        isOpen.style.display = "block";
    }
};

//Menu Active
function getClosest(elem, selector) {

    // Element.matches() polyfill
    if (!Element.prototype.matches) {
        Element.prototype.matches =
            Element.prototype.matchesSelector ||
            Element.prototype.mozMatchesSelector ||
            Element.prototype.msMatchesSelector ||
            Element.prototype.oMatchesSelector ||
            Element.prototype.webkitMatchesSelector ||
            function(s) {
                var matches = (this.document || this.ownerDocument).querySelectorAll(s),
                    i = matches.length;
                while (--i >= 0 && matches.item(i) !== this) {}
                return i > -1;
            };
    }

    // Get the closest matching element
    for (; elem && elem !== document; elem = elem.parentNode) {
        if (elem.matches(selector)) return elem;
    }
    return null;

};

function activateMenu() {
    var menuItems = document.getElementsByClassName("sub-menu-item");
    if (menuItems) {

        var matchingMenuItem = null;
        for (var idx = 0; idx < menuItems.length; idx++) {
            if (menuItems[idx].href === window.location.href) {
                matchingMenuItem = menuItems[idx];
            }
        }

        if (matchingMenuItem) {
            matchingMenuItem.classList.add('active');
            var immediateParent = getClosest(matchingMenuItem, 'li');
            if (immediateParent) {
                immediateParent.classList.add('active');
            }

            var parent = getClosest(matchingMenuItem, '.parent-menu-item');
            if (parent) {
                parent.classList.add('active');
                var parentMenuitem = parent.querySelector('.menu-item');
                if (parentMenuitem) {
                    parentMenuitem.classList.add('active');
                }
                var parentOfParent = getClosest(parent, '.parent-parent-menu-item');
                if (parentOfParent) {
                    parentOfParent.classList.add('active');
                }
            } else {
                var parentOfParent = getClosest(matchingMenuItem, '.parent-parent-menu-item');
                if (parentOfParent) {
                    parentOfParent.classList.add('active');
                }
            }
        }
    }
}


//Admin Menu
function activateSidebarMenu() {
    var current = location.pathname.substring(location.pathname.lastIndexOf('/') + 1);
    if (current !== "" && document.getElementById("sidebar")) {
        var menuItems = document.querySelectorAll('#sidebar a');
        for (var i = 0, len = menuItems.length; i < len; i++) {
            if (menuItems[i].getAttribute("href").indexOf(current) !== -1) {
                menuItems[i].parentElement.className += " active";
                if (menuItems[i].closest(".sidebar-submenu")) {
                    menuItems[i].closest(".sidebar-submenu").classList.add("d-block");
                }
                if (menuItems[i].closest(".sidebar-dropdown")) {
                    menuItems[i].closest(".sidebar-dropdown").classList.add("active");
                }
            }
        }
    }
}

if (document.getElementById("close-sidebar")) {
    document.getElementById("close-sidebar").addEventListener("click", function() {
        document.getElementsByClassName("page-wrapper")[0].classList.toggle("toggled");
    });
}

// Clickable Menu
if (document.getElementById("navigation")) {
    var elements = document.getElementById("navigation").getElementsByTagName("a");
    for (var i = 0, len = elements.length; i < len; i++) {
        elements[i].onclick = function(elem) {
            if (elem.target.getAttribute("href") === "javascript:void(0)") {
                var submenu = elem.target.nextElementSibling.nextElementSibling;
                submenu.classList.toggle('open');
            }
        }
    }
}

if (document.getElementById("sidebar")) {
    var elements = document.getElementById("sidebar").getElementsByTagName("a");
    for (var i = 0, len = elements.length; i < len; i++) {
        elements[i].onclick = function(elem) {
            if (elem.target.getAttribute("href") === "javascript:void(0)") {
                elem.target.parentElement.classList.toggle("active");
                elem.target.nextElementSibling.classList.toggle("d-block");
            }
        }
    }
}

// Menu sticky
function windowScroll() {
    var navbar = document.getElementById("topnav");
    if (navbar === null) {

    } else if (document.body.scrollTop >= 50 ||
        document.documentElement.scrollTop >= 50) {
        navbar.classList.add("nav-sticky");
    } else {
        navbar.classList.remove("nav-sticky");
    }
}

window.addEventListener('scroll', (ev) => {
    ev.preventDefault();
    windowScroll();
})

// back-to-top
window.onscroll = function() {
    scrollFunction();
};

function scrollFunction() {
    var mybutton = document.getElementById("back-to-top");
    if (mybutton === null) {

    } else if (document.body.scrollTop > 500 || document.documentElement.scrollTop > 500) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
}

function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

//Feather icon
feather.replace();

// dd-menu
if (document.getElementsByClassName("dd-menu")) {
    var ddmenu = document.getElementsByClassName("dd-menu");
    for (var i = 0, len = ddmenu.length; i < len; i++) {
        ddmenu[i].onclick = function(elem) {
            elem.stopPropagation();
        }
    }
}

//ACtive Sidebar
(function() {
    var current = location.pathname.substring(location.pathname.lastIndexOf('/') + 1);;
    if (current === "") return;
    var menuItems = document.querySelectorAll('.sidebar-nav a');
    for (var i = 0, len = menuItems.length; i < len; i++) {
        if (menuItems[i].getAttribute("href").indexOf(current) !== -1) {
            menuItems[i].parentElement.className += " active";
        }
    }
})();


//Validation Shop Checkouts
(function() {
    'use strict'
    if (document.getElementsByClassName('needs-validation').length > 0) {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.querySelectorAll('.needs-validation')
        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function(form) {
                form.addEventListener('submit', function(event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
    }
})();

//Tooltip
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
});

function CheckTitle(text) {
    var fullname = /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]{14,}(?:[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+){0,2}$/;
    if(!fullname.test(text.value)){
        text.setCustomValidity('Title không hợp lệ');
    }    
    else {
        text.setCustomValidity('');
    }
    return true;
}

function CheckPrice(text) {
    var phone = /([0-9.]{5,})\b/;
    if(!phone.test(text.value)){
        text.setCustomValidity('Giá không hợp lệ');
    }    
    else {
        text.setCustomValidity('');
    }
    return true;
}

// Vanh
function CheckFullName(text) {
    var fullname = /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]{4,}(?:[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+){0,2}$/;
    if(!fullname.test(text.value)){
        text.setCustomValidity('Họ tên không hợp lệ');
    }    
    else {
        text.setCustomValidity('');
    }
    return true;
}

// Vanh
function CheckPhone(text) {
    var phone = /(84|0[3|5|7|8|9])+([0-9]{8})\b/;
    
// Đây là mẫu biểu thức chính quy khớp với các số điện thoại ở Việt Nam, 
// bắt đầu bằng "84" hoặc "03", "05", "07", "08" hoặc "09" theo sau là tám chữ số bất kỳ.
    
// (84|0[3|5|7|8|9])+ khớp với tiền tố của số điện thoại, có thể là "84" (mã quốc gia của Việt Nam) hoặc "0" theo sau là "3", " 5", "7", "8" hoặc "9". Dấu + cho biết nhóm này có thể xuất hiện một hoặc nhiều lần, vì vậy số điện thoại có thể có nhiều tiền tố.
// ([0-9]{8}) khớp với tám chữ số bất kỳ theo sau tiền tố.
// \b là ranh giới từ, đảm bảo rằng số điện thoại không phải là một phần của chuỗi văn bản lớn hơn.
    if(!phone.test(text.value)){
        text.setCustomValidity('Số điện thoại không hợp lệ');
    }    
    else {
        text.setCustomValidity('');
    }
    return true;
}


// Vanh
function CheckUserName(text) {
    var username = /^(?!.*\.\.)(?!.*\.$)[^\W][\w.]{0,29}$/;
// Chuỗi không được bắt đầu hoặc kết thúc bằng dấu chấm (ví dụ: ".")
// Chuỗi có thể chứa các ký tự chữ và số và dấu chấm (ví dụ: ".")
// Chuỗi phải dài từ 1 đến 30 ký tự
// Đây là bảng phân tích các phần khác nhau của biểu thức chính quy:
//
// ^ - Khớp với phần đầu của chuỗi
// (?!.*\.\.) - Tra cứu phủ định để đảm bảo không có hai dấu chấm liên tiếp trong chuỗi
// (?!.*\.$) - Nhìn trước phủ định để đảm bảo rằng chuỗi không kết thúc bằng dấu chấm
// [^\W] - Khớp với bất kỳ ký tự không phải khoảng trắng nào (tức là ký tự chữ và số)
// [\w.]{0,29} - So khớp bất kỳ sự kết hợp nào của ký tự chữ và số và dấu chấm, dài từ 0 đến 29 ký tự
// $ - Khớp với phần cuối của chuỗi
    if(!username.test(text.value)){
        text.setCustomValidity('Tên đăng nhập không hợp lệ');
    }    
    else {
        text.setCustomValidity('');
    }
    return true;
}


// Vanh
function CheckEmail(text) {
    var email = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/; // regex

//  ^ - Bắt đầu chuỗi
// [\w-\.]+ - Một hoặc nhiều ký tự từ, dấu gạch ngang hoặc dấu chấm (khớp với tên người dùng email)
// @ - Khớp với ký hiệu "@"
// ([\w-]+\.)+ - Một hoặc nhiều lần xuất hiện của một hoặc nhiều ký tự từ hoặc dấu gạch nối, theo sau là dấu chấm (khớp với tên miền email)
// [\w-]{2,4} - Hai đến bốn ký tự từ hoặc dấu gạch ngang (khớp với tên miền cấp cao nhất) - Tên miền 2 đến 4 kí tự 
// $ - Kết thúc chuỗi
// email có thể là Vanhleg2301-vanh@vanhvanh.edu

    if(!email.test(text.value)){
        // Test dùng để tìm 'email' trong giá trị của 'text' đc truyền vào. https://www.w3schools.com/jsref/jsref_regexp_test.asp
        text.setCustomValidity('Email không hợp lệ'); // Đặt thuộc tính 'validationMessage' của phần tử đầu vào. https://www.w3schools.com/js/js_validation_api.asp
    }    
    else {
        text.setCustomValidity('');
    }
    return true;
}

// Vanh
function CheckPassword(text) {
    var pass = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
    
// ^ - bắt đầu chuỗi;
// (?=.*\d) - nhìn trước tích cực để đảm bảo rằng chuỗi chứa ít nhất một chữ số (\d)
// (?=.*[a-z]) - tìm kiếm tích cực để đảm bảo rằng chuỗi chứa ít nhất một chữ cái viết thường (a-z)
// (?=.*[A-Z]) - tìm kiếm tích cực để đảm bảo rằng chuỗi chứa ít nhất một chữ cái viết hoa (A-Z)
// (?=.*[a-zA-Z]) - nhìn trước tích cực để đảm bảo rằng chuỗi chứa ít nhất một ký tự chữ cái (a-zA-Z)
// .{8,} - khớp với bất kỳ ký tự nào (ngoại trừ dòng mới), với độ dài tối thiểu là 8 ký tự
// $ - cuối chuỗi
    if(!pass.test(text.value)){
        text.setCustomValidity('Mật khẩu không hợp lệ (Cần có ít nhất 8 ký tự bao gồm viết hoa và ký tự đặc biệt)!');
    }    
    else {
        text.setCustomValidity('');
    }
    return true;
}


// Vanh
function CheckRePassword(text) {
    var password = document.getElementById('password').value;
    if(password != text.value){
        text.setCustomValidity('Mật khẩu không khớp!');
    }    
    else {
        text.setCustomValidity('');
    }
    return true;
}

// Vanh
function CheckNumber(text) {
    var number = /([0-9])+/;
    if(!number.test(text.value)){
        text.setCustomValidity('Sai định dạng');
    }else if (text.value < 0){
        text.setCustomValidity('Yêu cầu lớn hơn 0');
    }   
    else {
        text.setCustomValidity('');
    }
    return true;
}
