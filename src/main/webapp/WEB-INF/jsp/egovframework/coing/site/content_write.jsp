<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>

<div class="row">
	<div class="col-lg-2">
		<div class="block">
			<div class="block-content">
				<form action="${REQUEST_URI}" method="get" class="form-search" role="form">
					<fieldset>
						<legend class="sr-only">검색 폼</legend>
						<div class="form-group">
							<select name="sinId" id="sinId" class="form-control" onchange="this.form.submit();">
							<option value="">+ 사이트선택</option>
							<c:forEach var="result" items="${siteList}" varStatus="status">
							<option value="${result.sinId}" <c:if test="${searchSiteMenuVO.sinId eq result.sinId}">selected="selected"</c:if>><c:out value="${result.sinName}" /></option>
							</c:forEach>
							</select>
						</div>
					</fieldset>
				</form>
			</div>
		</div>	
		<div class="block">
			<div class="block-content">
				<link rel="stylesheet" href="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.css">
				<script type="text/javascript" src="${CONTEXT_PATH}/assets/js/plugins/dtree/dtree.js"></script>
				<script type="text/javascript">
				var d = new dTree('d');
				d.add(<c:out value="${rootMenuId}"/>,-1,"홈", "${REQUEST_URI}");
				<c:forEach var="result" items="${allSiteMenuList}" varStatus="status">
				<c:if test="${result.smeParntsId > 0}">
				d.add(<c:out value='${result.smeId}' />, <c:out value='${result.smeParntsId}' />, "<c:out value='${result.smeName}' />", "?sinId=<c:out value='${result.sinId}' />&smeId=<c:out value='${result.smeId}' />", "", "", "");
				</c:if>
				</c:forEach>
				document.write(d);
				</script>
			</div>
		</div>
	</div>
	<div class="col-lg-10">
		<div class="block">
			<div class="block-head">
				<div class="pull-left">
					<h1><c:out value="${ADMIN_MENU_VO.amePname}"/> <c:choose><c:when test="${command == 'insert'}"><spring:message code="common.insert" /></c:when><c:otherwise><spring:message code="common.update" /></c:otherwise></c:choose></h1>
				</div>
				<div class="pull-right">
					<c:if test="${command eq 'update'}">
					<div class="pd-r-xs pd-t-xs">
						<a href="javascript:post_url('', 'act=publish&sinId=<c:out value="${searchSiteMenuVO.sinId}"/>&smeId=<c:out value="${searchSiteMenuVO.smeId}"/>&contentId=<c:out value="${writeSiteContent.contentId}"/>','정말로 출판하시겠습니까?');" class="btn btn-default">출판하기</a>
					</div>
					</c:if>					
				</div>
			</div>		
			<div class="block-content">
				<c:choose>
					<c:when test="${siteMenuVO.smeType == 'HTML'}">
						<form:form commandName="writeSiteContent" action="?act=write" class="form-horizontal" role="form">
							<input type="hidden" name="command" value="<c:out value='${command}'/>" />
							<input type="hidden" name="sinId" value="<c:out value='${searchSiteMenuVO.sinId}'/>" />
							<input type="hidden" name="smeId" value="<c:out value='${searchSiteMenuVO.smeId}'/>" />
							<input type="hidden" name="contentId" value="<c:out value='${writeSiteContent.contentId}'/>" />
							<fieldset>
								<legend class="sr-only">콘텐츠 관리 폼</legend>
								<div class="form-group">
									<span class="col-sm-2 text-right"><label class="control-label">상위메뉴</label></span>
									<div class="col-sm-10">
										<div class="form-control-static">
											홈
											<c:forEach var="result" items="${parntsSiteMenuList}" varStatus="status">
											&gt; <c:out value="${result.smeName}" />
											</c:forEach>
										</div>
									</div>
								</div>
								<div class="form-group">
									<span class="col-sm-2 text-right"><label class="control-label">메뉴명</label></span>
									<div class="col-sm-10">
										<p class="form-control-static"><c:out value="${siteMenuVO.smeName}"/></p>
									</div>
								</div>
								<div class="form-group">
									<span class="col-sm-2 text-right"><label class="control-label">마지막 파일변경</label></span>
									<div class="col-sm-10">
										<p class="form-control-static"><c:out value="${lastUpdtDate}"/></p>
									</div>
								</div>
								<div class="form-group">
									<span class="col-sm-2 text-right"><label class="control-label">마지막 DB변경</label></span>
									<div class="col-sm-10">
										<p class="form-control-static"><c:out value="${fn:substring(writeSiteContent.scoUpdtDttm,0,19)}"/></p>
									</div>
								</div>
								<div class="form-group">
									<span class="col-sm-2 text-right"><label for="bcontent" class="control-label">내용</label></span>
									<div class="col-sm-10">
										<form:textarea path="content" id="bcontent" class="form-control"/>
										<ckeditor:replace replace="bcontent" basePath="${CONTEXT_PATH}/assets/ckeditor/" />
									</div>
								</div>
								<div class="bd-t md-t-md pd-t-md"></div>
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.save" /></button>
								</div>
							</fieldset>
						</form:form>
					</c:when>
					<c:otherwise>
						콘텐츠 메뉴타입(HTML 및 JSP)만 편집가능합니다.
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>
