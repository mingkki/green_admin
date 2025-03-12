<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<h1>안내</h1>
<spring:message code='${message}' />
<button>홈으로</button> <button onclick="history.back(-1);">뒤로가기</button>