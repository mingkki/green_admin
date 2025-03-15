<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">
	<div class="col-lg-12">
		<div class="block">
			<div class="block-head">
				<h1>
					<c:out value="${ADMIN_MENU_VO.amePname}"/> 
					<c:choose>
						<c:when test="${command == 'insert'}"><spring:message code="common.insert" /></c:when>
						<c:otherwise><spring:message code="common.update" /></c:otherwise>
					</c:choose>
				</h1>
			</div>
			<div class="block-content">
				<form:form commandName="writeBoardInfo" action="?act=write" class="form-horizontal" role="form">
					<input type="hidden" name="command" value="<c:out value='${command}' />" />
					<input type="hidden" name="returnQueryString" value="<c:out value='${searchBoardInfoVO.queryString}' escapeXml='false' />"/>
					<input type="hidden" name="brdId" value="${searchBoardInfoVO.brdId}"/>
					<fieldset>
						<legend class="sr-only">등록/수정</legend>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label for="brdId" class="control-label">* 게시판 ID</label></span>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${command eq 'insert'}">
										<p class="form-control-static">게시판 아이디는 자동으로 생성됩니다.</p>
									</c:when>
									<c:otherwise>
										<form:input path="brdId" class="form-control" disabled="true" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdName"><span class="required" title="필수입력항목">*</span> 게시판 이름</label></span>
							<div class="col-sm-10">
								<form:input path="brdName" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdSummary">게시판 설명</label></span>
							<div class="col-sm-10">
								<form:textarea path="brdSummary" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdSkin"><span class="required" title="필수입력항목">*</span> 스킨</label></span>
							<div class="col-sm-10">							
								<form:select path="brdSkin" class="form-control required">
								<c:forEach var="result" items="${skinList}" varStatus="status">
								<form:option value="${result.key}">${result.value}</form:option>
								</c:forEach>								
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdSubjectLen"><span class="required" title="필수입력항목">*</span> 글제목 길이</label></span>
							<div class="col-sm-10">
								<form:input path="brdSubjectLen" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdNewPeriod"><span class="required" title="필수입력항목">*</span> 새글 표시 기간</label></span>
							<div class="col-sm-10">
								<form:input path="brdNewPeriod" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdPerPage"><span class="required" title="필수입력항목">*</span> 페이지당 글 개수</label></span>
							<div class="col-sm-10">
								<form:input path="brdPerPage" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label"><span class="required" title="필수입력항목">*</span> 사용유무</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="brdUseYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdUseYn" value="N"/>미사용</label>	
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdHeadHtml">상단 설명</label></span>
							<div class="col-sm-10">
								<form:textarea path="brdHeadHtml" class="form-control" />
								<div class="help-block">
									HTML 및 JavaScript를 사용 하실 수 있습니다.
								</div>								
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdTailHtml">하단 설명</label></span>
							<div class="col-sm-10">
								<form:textarea path="brdTailHtml" class="form-control" />
								<div class="help-block">
									HTML 및 JavaScript를 사용 하실 수 있습니다.
								</div>								
							</div>
						</div>						
						<div class="bd-t pd-t-sm"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">답글</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdReplyYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdReplyYn" value="N"/>미사용</label>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">비밀글</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdSecretAt" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdSecretAt" value="N"/>미사용</label>
								<label class="radio-inline"><form:radiobutton path="brdSecretAt" value="A"/>무조건</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">카테고리</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdCategoryYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdCategoryYn" value="N"/>미사용</label>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">게시기간</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdPeriodYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdPeriodYn" value="N"/>미사용</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">게시글 DB삭제</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdRestoreYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdRestoreYn" value="N"/>미사용</label>
								<div class="help-block">
								게시글 DB삭제 기능 사용시 복원할 수 없습니다.<br/>(일반 사용자가 삭제시 복원 할 수 없습니다.)
								</div>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">삭제 게시글 보기</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdViewDelYn" value="Y"/>표출</label>
								<label class="radio-inline"><form:radiobutton path="brdViewDelYn" value="N"/>미표출</label>
								<div class="help-block">
								삭제 게시물을 목록에 표출 여부를 설정합니다.
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">상단공지</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdNoticeYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdNoticeYn" value="N"/>미사용</label>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">에디터</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdEditorYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdEditorYn" value="N"/>미사용</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">첨부파일</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdUploadYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdUploadYn" value="N"/>미사용</label>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">처리내역</label></span>
							<div class="col-sm-4">
								<label class="radio-inline"><form:radiobutton path="brdProgressYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdProgressYn" value="N"/>사용</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">첨부파일 개수</label></span>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="brdUploadCnt" class="form-control" />
									<span class="input-group-addon">개</span>
								</div>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">첨부파일 크기</label></span>
							<div class="col-sm-4">
								<div class="input-group">
									<form:input path="brdUploadSize" class="form-control" />
									<span class="input-group-addon">MB</span>
								</div>
								<div class="help-block">
									최대 500MB 이며 0으로 설정 시 시스템 허용 용량으로 설정됩니다.
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brd_upload_ext"><span class="required" title="필수입력항목">*</span> 첨부파일 허용 확장자</label></span>
							<div class="col-sm-10">
								<form:input path="brdUploadExt" class="form-control" />
								<div class="help-block">
								입력된 확장자만 허용 되며 여러건은 쉼표(,)로 구분 합니다. 공백이면 모든파일이 허용 됩니다. 예) zip,rar,hwp,xls,xlsx,doc,docx,ppt,pptx,pdf,jpg,gif,png
								</div>
							</div>
						</div>
						<div class="form-group" style="display: none">
							<span class="col-sm-2 text-right"><label class="control-label">IP 체크</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="brdIpGubun" value="P"/>내용 허용</label>
								<label class="radio-inline"><form:radiobutton path="brdIpGubun" value="L"/>내용 차단</label>
								<label class="radio-inline"><form:radiobutton path="brdIpGubun" value="N"/>사용 안함</label>
							</div>
						</div>
						<div class="form-group" style="display: none">
							<span class="col-sm-2 text-right"><label class="control-label" for="brd_check_ip">IP 내역</label></span>
							<div class="col-sm-10">
								<form:textarea path="brdCheckIp" class="form-control" />
								<div class="help-block">
								IP 내역을 입력하며 여러건은 쉼표(,)로 구분 합니다. 예) 123.123.123.123,456.456.456.456
								</div>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label">금지 단어</label></span>
							<div class="col-sm-10">
								<label class="radio-inline"><form:radiobutton path="brdLimitWordYn" value="Y"/>사용</label>
								<label class="radio-inline"><form:radiobutton path="brdLimitWordYn" value="N"/>미사용</label>								
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brd_limit_word">금지단어</label></span>
							<div class="col-sm-10">
								<form:textarea path="brdLimitWord" class="form-control" />
								<div class="help-block">
								금지 단어에 입력된 단어는 게시물 작성 시 등록되지 않습니다. 여러건은 쉼표(,)로 구분 합니다.
								</div>
							</div>
						</div>
						<div class="bd-t pd-t-sm"></div>
						<div class="bd-t pd-t-sm"></div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdPermitWrite">쓰기가능 권한</label></span>
							<div class="col-sm-1">
								<form:select path="brdPermitWrite" class="form-control">
								<form:option value="0">전체</form:option>
								<c:forEach var="result" items="${levelList}" varStatus="status">
								<form:option value="${result.lvlId}"><c:out value="${result.lvlName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-1">
								<label class="checkbox-inline"><form:checkbox path="brdRealnameWriteYn" value="Y"/>실명인증</label>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">쓰기가능 그룹</label></span>
							<div class="col-sm-6">
								<c:forEach var="result" items="${groupList}" varStatus="status">
								<label class="checkbox-inline">
									<form:checkbox path="brdLimitWriteArr" value="${result.grpId}"/><c:out value="${result.grpName}"/>
								</label>
								</c:forEach>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdWriteBtnYn">쓰기버튼</label></span>
							<div class="col-sm-10">
								<label class="checkbox-inline"><form:checkbox path="brdWriteBtnYn" value="Y"/>권한에 상관없이 쓰기버튼 출력</label>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brd_permit_reply">답글가능 권한</label></span>
							<div class="col-sm-1">
								<form:select path="brdPermitReply" class="form-control">
									<form:option value="0">전체</form:option>
									<c:forEach var="result" items="${levelList}" varStatus="status">
										<form:option value="${result.lvlId}"><c:out value="${result.lvlName}"/></form:option>
									</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-1">
								<label class="checkbox-inline"><form:checkbox path="brdRealnameReplyYn" value="Y"/>실명인증</label>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">답글가능그룹</label></span>
							<div class="col-sm-6">
								<c:forEach var="result" items="${groupList}" varStatus="status">
									<label class="checkbox-inline">
										<form:checkbox path="brdLimitReplyArr" value="${result.grpId}"/><c:out value="${result.grpName}"/>
									</label>
								</c:forEach>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdPermitUpload">업로드가능 권한</label></span>
							<div class="col-sm-1">
								<form:select path="brdPermitUpload" class="form-control">
								<form:option value="0">전체</form:option>
								<c:forEach var="result" items="${levelList}" varStatus="status">
								<form:option value="${result.lvlId}"><c:out value="${result.lvlName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-1">
								<label class="checkbox-inline"><form:checkbox path="brdRealnameUploadYn" value="Y"/>실명인증</label>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">업로드가능 그룹</label></span>
							<div class="col-sm-6">
								<c:forEach var="result" items="${groupList}" varStatus="status">
								<label class="checkbox-inline">
									<form:checkbox path="brdLimitUploadArr" value="${result.grpId}"/><c:out value="${result.grpName}"/>
								</label>
								</c:forEach>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdPermitDownload">다운로드가능 권한</label></span>
							<div class="col-sm-1">
								<form:select path="brdPermitDownload" class="form-control">
								<form:option value="0">전체</form:option>	
								<c:forEach var="result" items="${levelList}" varStatus="status">
								<form:option value="${result.lvlId}"><c:out value="${result.lvlName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-1">
								<label class="checkbox-inline"><form:checkbox path="brdRealnameDownloadYn" value="Y"/>실명인증</label>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">다운로드가능 그룹</label></span>
							<div class="col-sm-6">
								<c:forEach var="result" items="${groupList}" varStatus="status">
								<label class="checkbox-inline">
									<form:checkbox path="brdLimitDownloadArr" value="${result.grpId}"/><c:out value="${result.grpName}"/>
								</label>
								</c:forEach>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdPermitNotice">공지가능 권한</label></span>
							<div class="col-sm-1">
								<form:select path="brdPermitNotice" class="form-control">
								<form:option value="0">전체</form:option>								
								<c:forEach var="result" items="${levelList}" varStatus="status">
								<form:option value="${result.lvlId}"><c:out value="${result.lvlName}"/></form:option>
								</c:forEach>
								</form:select>
							</div>
							<div class="col-sm-1">
							</div>							
							<span class="col-sm-2 text-right"><label class="control-label">공지가능 그룹</label></span>
							<div class="col-sm-6">
								<c:forEach var="result" items="${groupList}" varStatus="status">
								<label class="checkbox-inline">
									<form:checkbox path="brdLimitNoticeArr" value="${result.grpId}"/><c:out value="${result.grpName}"/>
								</label>
								</c:forEach>
							</div>
						</div>
						<div class="form-group">
							<span class="col-sm-2 text-right"><label class="control-label" for="brdPermitEditor">에디터가능 권한</label></span>
							<div class="col-sm-1">
								<form:select path="brdPermitEditor" class="form-control">
								<form:option value="0">전체</form:option>
								<c:forEach var="result" items="${levelList}" varStatus="status">
								<form:option value="${result.lvlId}"><c:out value="${result.lvlName}"/></form:option>
								</c:forEach>
								</form:select>								
							</div>
							<div class="col-sm-1">
								<label class="checkbox-inline"><form:checkbox path="brdRealnameEditorYn" value="Y"/>실명인증</label>
							</div>
							<span class="col-sm-2 text-right"><label class="control-label">에디터가능 그룹</label></span>
							<div class="col-sm-6">
								<c:forEach var="result" items="${groupList}" varStatus="status">
								<label class="checkbox-inline">
									<form:checkbox path="brdLimitEditorArr" value="${result.grpId}"/><c:out value="${result.grpName}"/>
								</label>
								</c:forEach>
							</div>
						</div>
						<div class="bd-t pd-t-sm"></div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary pd-l-lg pd-r-lg"><spring:message code="common.insert" /></button>
								<a href="?<c:out value='${searchBoardInfoVO.queryString}' />" class="btn btn-default pd-l-lg pd-r-lg"><spring:message code="common.list" /></a>
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
	$("#writeBoardInfo").validate();
});
</script>