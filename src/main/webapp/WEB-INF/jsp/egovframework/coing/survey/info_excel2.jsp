<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt"%>  

<%  
  String file_name ="survey_excel2";
  String ExcelName  = new String(file_name.getBytes(),"UTF-8")+".xls";
  response.setContentType("application/vnd.ms-excel");
  response.setHeader("Content-Disposition", "attachment; filename="+ExcelName);
  response.setHeader("Content-Description", "JSP Generated Data");
  response.setHeader("Pragma", "no-cache");    
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">	
	<meta name="keywords" content="">
	<meta name="description" content="">	
</head>
<body>
<h1><c:out value='${surveyInfoVO.svinTitle}'/></h1>
<table border="1">
	<thead>
		<tr>
			<th scope="col">구분</th>
			<c:forEach var="result" items="${surveyQuestionList}" varStatus="status">
			<th scope="col"><c:out value="${result.svquTitle}"/></th>
			</c:forEach>
		</tr>		
	</thead>
	<tbody>
		<c:forEach var="result" items="${surveyVoteList}" varStatus="status">
		<c:set var="vo" value="${surveyVoteResultList[result.svvoId]}"/>
		<tr>
			<td>
				<c:choose>
					<c:when test="${not empty result.svvoUserNm}"><c:out value="${result.svvoUserNm}"/></c:when>
					<c:when test="${not empty result.svvoUserId}"><c:out value="${result.svvoUserId}"/></c:when>
					<c:otherwise><c:out value="${result.svvoRegIp}"/></c:otherwise>
				</c:choose>
			</td>
			<c:forEach var="result2" items="${surveyQuestionList}" varStatus="status">
			<c:set var="vo2" value="${vo[result2.svquId]}"/>
			<td>${vo2}</td>
			</c:forEach>
		</tr>
		</c:forEach>
	</tbody>
</table>