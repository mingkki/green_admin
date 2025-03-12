<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*, java.text.*"  %>
<%@ page import="egovframework.coing.site.vo.SiteInfoVO"%>
<%@ page import="egovframework.coing.site.vo.SiteMenuVO"%>
<%
SiteInfoVO siteInfoVO = (SiteInfoVO)request.getAttribute("siteInfoVO");
SiteMenuVO siteMenuVO = (SiteMenuVO)request.getAttribute("siteMenuVO");

String fileNm = "["+siteInfoVO.getSinName()+"]";
fileNm += (siteMenuVO.getSmeId() > 0 ? " " + siteMenuVO.getMenuPath() + " " : "");
fileNm += "콘텐츠만족도";

String excelFileNm = new String((fileNm.replace(" ", "_")).getBytes("KSC5601"),"8859_1");
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "attachment; filename="+excelFileNm+".xls");
response.setHeader("Content-Description", "JSP Generated Data");
response.setHeader("Pragma", "no-cache");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=utf-8">
</head>
<body>
<h1 style="text-align:center;">[<c:out value="${siteInfoVO.sinName}"/>] <c:if test="${not empty siteMenuVO}"><c:out value="${siteMenuVO.menuPath}"/></c:if> 콘텐츠만족도</h1>
<table border="1" width="100%">
	<thead>
		<tr>
			<th scope="col" style="text-align:center;">메뉴위치</th>
			<th scope="col" style="text-align:center;">메뉴명</th>
			<th scope="col" style="text-align:center;">작성자</th>
			<th scope="col" style="text-align:center;">내용</th>
			<th scope="col" style="text-align:center;">점수</th>
			<th scope="col" style="text-align:center;">작성일</th>
			<th scope="col" style="text-align:center;">작성IP</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td style="text-align:center;"><c:out value="${result.menuPath}" /></td>
				<td style="text-align:center;"><c:out value="${result.smeName}" /></td>
				<td style="text-align:center;"><c:out value="${result.writerNm}" />(<c:out value="${result.writerId}" />)</td>
				<td style="text-align:center;"><c:out value="${result.content}" /></td>
				<td style="text-align:center;"><c:out value="${result.grade}" /></td>
				<%-- <td style="text-align:center;"><c:out value="${result.scoRegDttm}" /></td>
				<td style="text-align:center;"><c:out value="${result.scoRegIp}" /></td> --%>
			</tr>
		</c:forEach>
	</tbody>
</table>
</body>
</html>