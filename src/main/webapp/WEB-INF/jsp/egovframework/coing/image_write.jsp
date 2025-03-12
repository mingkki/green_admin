<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ckeditor" uri="http://ckeditor.com" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<div class="block-head">
				<h1><c:out value="${ADMIN_MENU_VO.amePname}"/> <c:choose><c:when test="${command == 'insert'}"><spring:message code="common.insert" /></c:when><c:otherwise><spring:message code="common.update" /></c:otherwise></c:choose></h1>
			</div>			
			<div class="block-content">
				<form:form commandName="writeImageInfo" action="?act=write" enctype="multipart/form-data" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchImageInfoVO.queryString}' escapeXml='false' />"/>
					<input type="hidden" name="imgId" value="${searchImageInfoVO.imgId}"/>
					<fieldset>
						<legend class="sr-only">등록정보</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="imgTitle" class="control-label">* 타이틀</label></span>
							<div class="col-sm-10">
								<form:input path="imgTitle" class="form-control required" maxlength="120" />
							</div>
						</div>						
						<div class="form-group">
							<span class="col-sm-2  text-right"><label for="imgUseYn" class="control-label">사용유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="imgUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="imgUseYn" value="N"/>미사용</label>								
							</div>
						</div>
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">파일첨부</label></span>
							<div class="col-sm-10">
								<c:forEach var="result" begin="0" end="10" varStatus="status">
								<c:set var="fileVO" value="${imageFileList[status.index]}"/>
								<div class="mg-b-xs">
									<input type="file" name="uploadFile" id="uploadFile[]" class="form-control"  />
									<c:if test="${!empty fileVO}">									
										<div class="mg-t-xs">									
											<input type="text" name="uploadFileMemo" class="form-control" placeholder="설명글을 입력해 주세요." value="<c:out value='${fileVO.imfMemo}'/>" />
											<label class="checkbox-inline">
												<input type="checkbox" name="deleteUploadFile" value="<c:out value='${fileVO.imfNo}'/>" />
												삭제 (<img src="/imgview.do?file=<c:out value='${fileVO.imfName}'/>" width="75" alt=""/>)
											</label>
										</div>
									</c:if>
									<c:if test="${empty fileVO}">
										<div class="mg-t-xs">
											<input type="text" name="uploadFileMemo" class="form-control" placeholder="설명글을 입력해 주세요." value="" />
										</div>
									</c:if>
								</div>
								<div class="bd-t mg-t-md pd-t-md"></div>
								</c:forEach>
							</div>
						</div>						
						<div class="bd-t mg-t-md pd-t-md"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchImageInfoVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function()
{
	$("#writeImageInfo").validate();
});
</script>