<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt_rt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%  
  String file_name ="survey_excel";
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
<table border="1">
<tbody>
	<tr>
		<th scope="row">제목</th>
		<td><c:out value='${surveyInfoVO.svinTitle}'/></td>
	</tr>
	<tr>
		<th scope="row">설문기간</th>
		<td><c:out value='${surveyInfoVO.svinStartDt}'/> ~ <c:out value='${surveyInfoVO.svinEndDt}'/></td>
	</tr>
	<tr>
		<th scope="row">참여인원</th>
		<td><c:out value='${surveyInfoVO.voteCnt}'/>명</td>
	</tr>
	<tr>
		<th scope="row">내용</th>
		<td><c:out value='${surveyInfoVO.svinContent}' escapeXml='false'/></td>
	</tr>	
	<tr>
		<th scope="row">진행상태</th>
		<td>
			<c:choose>
				<c:when test="${result.progress == 'ING'}">진행</c:when>
				<c:when test="${result.progress == 'PRE'}">중비중</c:when>
				<c:otherwise>종료</c:otherwise>
			</c:choose>		
		</td>
	</tr>	
</tbody>
</table>
<br/>
<br/>
<table border="1">
<c:forEach var="question" items="${surveyQuestionList}" varStatus="status">
	<tr>
		<td colspan="2" align="center" style="background:#ddd;">
			<c:out value='${status.index+1}'/>. <c:out value='${question.svquTitle}'/>
			<c:if test="${question.svquType == 'B' || question.svquType == 'E'}">
				<%-- (<c:out value='${question.minChkedNum}'/>개 이상 선택해 주세요.) --%>
			</c:if>
		</td>
	</tr>
	<c:if test="${question.svquType == 'C'}">
	<tr>
		<td colspan="2">
			<c:if test="${fn:length(question.answerList) > 0}">
			<ul>
				<c:forEach var="answer" items="${question.answerList}" varStatus="status">
				<%-- <li><c:out value='${answer.remarks}'/></li> --%>
				</c:forEach>
			</ul>
			</c:if>
		</td>
	</tr>
	</c:if>
	<c:if test="${question.svquType != 'C'}">
		<c:forEach var="example" items="${question.exampleList}" varStatus="status">
		<c:set var="percent" value="${(example.answerCnt / question.totalAnswerCnt) * 100}" />
		<tr>
			<td>
				<c:out value='${example.svexTitle}'/>
				<c:if test="${question.svquType eq 'C'}">
					<c:if test="${fn:length(example.answerList) > 0}">
					<ul>
						<c:forEach var="answer" items="${example.answerList}" varStatus="status">
						<li><c:out value='${answer.svanRemarks}'/></li>
						</c:forEach>
					</ul>
					</c:if>
				</c:if>
			</td>
			<td>
				<c:choose>				
					<c:when test="${example.answerCnt > 0}"><c:out value='${example.answerCnt}' />명, <fmt_rt:formatNumber value="${percent}" maxFractionDigits="1" />%</div></c:when>
					<c:otherwise>0명, 0%</c:otherwise>
				</c:choose>				
			</td>
		</tr>
		</c:forEach>
	</c:if>
</c:forEach>
</table>
</body>
</html>