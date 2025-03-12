<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>회원님의 개인 정보 보호와 안전한 서비스 사용을 위해 로그인을 임시 차단합니다.</div>
<div>로그인이 일시적으로 잠겼습니다. 30분 후에 다시 시도해 주세요.</div>
<ul>
<li>- 마지막 로그인 시도: <c:out value="${resultVO.loginfailDttm}"/></li>
</ul>
아이디, 비밀번호를 잊으셨다면 아이디/비밀번호 찾기를 이용하시기 바랍니다.