var iSecond; //초단위로 환산
var timerchecker = null;
window.onload = function() {
    fncClearTime();
    initTimer();
}
 
function fncClearTime() {
    iSecond = 600 * 6;
}
 
Lpad = function(str, len) {
    str = str + "";
    while (str.length < len) {
        str = "0" + str;
    }
    return str;
}
 
initTimer = function() {
    var timer = document.getElementById("timer");
    rHour = parseInt(iSecond / 3600);
    rHour = rHour % 60;
 
    rMinute = parseInt(iSecond / 60);
    rMinute = rMinute % 60;
 
    rSecond = iSecond % 60;
 
    if (iSecond > 2) {
        timer.innerHTML = "&nbsp;" + Lpad(rMinute, 2)
                + "분 " + Lpad(rSecond, 2) + "초 ";
        iSecond--;
        timerchecker = setTimeout("initTimer()", 1000); // 1초 간격으로 체크
    } else {
		alert("자동 로그아웃 되었습니다.");
        logoutUser();
    }
}
 
function refreshTimer() {
    var xhr = initAjax();
    xhr.open("POST", baseUrl + "/timeoutchk.jsp", false);
    xhr.send();
    fncClearTime();
}
 
function logoutUser() {
    clearTimeout(timerchecker);
    var xhr = initAjax();
    xhr.open("POST", baseUrl + "/auth/logout.cs", false);
    xhr.send();
    location.reload();
}
 
function initAjax() { // 브라우저에 따른 AjaxObject 인스턴스 분기 처리
    var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}