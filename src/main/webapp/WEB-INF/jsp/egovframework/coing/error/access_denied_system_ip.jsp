<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>시스템에 접근하실 수 없습니다.</h1>
<div>접속하신 IP는 시스템에 접근하실 수 없습니다.</div>
<div>접속 IP : <c:out value="${remoteAddr}"/></div>