<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="func.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title><c:out value="${TITLE}"/></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  
  <link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/theme-d-hw-turquoise.css" rel="stylesheet" type="text/css" id="theme"/>
  <link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/ereservation/content_nj.css" rel="stylesheet" type="text/css"/>
  <link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/ereservation/sub_temp.css" rel="stylesheet" type="text/css"/>
  <link href="<c:out value='${CONTEXT_PATH}'/>/assets/css/ereservation/sub.css" rel="stylesheet" type="text/css"/>
  
  
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery/jquery.min.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/jquery/jquery-ui.min.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/bootstrap/bootstrap.min.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/plugins.js"></script>
  
  <script type="text/javascript" src="${CONTEXT_PATH}/validator.do"></script>
  
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/actions.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/sys.js"></script>
  <script type="text/javascript" src="<c:out value='${CONTEXT_PATH}'/>/assets/js/message-1.0.0.js"></script>
  <script type="text/javascript">
    var baseUrl = '${CONTEXT_PATH}';
  </script>
</head>
<body>
<div class="page-container">
  <div class="page-head">
    <ul class="page-head-elements pull-right">
      <li>
        <a href="${CONTEXT_PATH}/login/log/oneself.do" style="font-size:12px;">
          마지막 로그인 정보 : <c:out value="${fn:substring(LOGIN_INFO.lastloginDttm,0,16)}"/> / <c:out value="${LOGIN_INFO.lastloginIp}"/>
        </a>
      </li>
      <li>
        <script type="text/javascript" charset="utf-8" src="${CONTEXT_PATH}/assets/js/timeoutchk.js"></script>
        <a href="#" style="font-size:12px;">
          <span id="timer"></span>
          <button type="button" class="btn btn-default btn-xs" onclick="refreshTimer();">연장</button>
        </a>
      </li>
      <li><a href="${CONTEXT_PATH}/auth/logout.do" class="but dropdown-toggle" title="LOGOUT"><i class="fa fa-times"></i></a></li>
    </ul>
  </div>
  <div class="page-navigation">
    <div class="page-navigation-info">
      <a href="${CONTEXT_PATH}" class="logo">함께그린 CMS</a>
    </div>
    <div class="profile">
      <img src="${CONTEXT_PATH}/assets/img/admin_photo.jpg"/>
      <div class="profile-info">
        <a href="#" class="profile-title"><c:out value="${LOGIN_INFO.name}"/> (<c:out value="${LOGIN_INFO.id}"/>)</a>
        <span class="profile-subtitle">
                        	<c:choose>
                            <c:when test="${LOGIN_INFO.userLevel eq 99}">최고 관리자</c:when>
                            <c:when test="${LOGIN_INFO.userLevel eq 98}">사이트 관리자</c:when>
                            <c:when test="${LOGIN_INFO.userLevel eq 97}">사이트 부관리자</c:when>
                          </c:choose>
                        </span>
        <div class="profile-buttons">
          <div class="btn-group">
            <a class="but dropdown-toggle" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
            <ul class="dropdown-menu" role="menu">
              <li role="presentation" class="dropdown-header">Profile Menu</li>
              <li><a href="${CONTEXT_PATH}/myinfo.do">회원정보변경</a></li>
              <li><a href="${CONTEXT_PATH}/login/log/oneself.do">로그인 이력</a></li>
              <li class="divider"></li>
              <li><a href="${CONTEXT_PATH}/auth/logout.do">로그아웃</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <c:set var="userLevel" value="${LOGIN_INFO.userLevel}"/>
    <ul class="navigation">
      <%
        int userLevel = Integer.parseInt(pageContext.getAttribute("userLevel").toString()); //사용자 Level얻기
        
        List<AdminMenuVO> topmenuList = amh.getMenuList(1);
        if(topmenuList.size() > 0) {
          for(int i = 0; i < topmenuList.size(); i++) {
            AdminMenuVO topmenuVO = (AdminMenuVO) topmenuList.get(i);
            
            if(userLevel <= 90 && (topmenuVO.getAmeId() == 2 || topmenuVO.getAmeId() == 24 || topmenuVO.getAmeId() == 26)) {
              continue;
            }
            
            List<AdminMenuVO> submenuList = amh.getMenuList(topmenuVO.getAmeId());
            boolean chldrns = submenuList.size() > 0
              ? true
              : false;
            
            String liClass = "";
            if(PARNTS_ADMIN_MENU_LIST.get(1) != null) {
              if(PARNTS_ADMIN_MENU_LIST.get(1).getAmeId() == topmenuVO.getAmeId()) {
                liClass = " class=\"active open\"";
              }
            }
            
            out.println(String.format("<li %s>%s", liClass, getMenuLink(request, topmenuVO)));
            
            if(chldrns) {
              out.println("<ul>");
              for(int j = 0; j < submenuList.size(); j++) {
                AdminMenuVO submenuVO = (AdminMenuVO) submenuList.get(j);
                
                List<AdminMenuVO> sub2menuList = amh.getMenuList(submenuVO.getAmeId());
                boolean chldrns2 = sub2menuList.size() > 0
                  ? true
                  : false;
                
                String liClass2 = "";
                if(PARNTS_ADMIN_MENU_LIST.get(2) != null) {
                  if(PARNTS_ADMIN_MENU_LIST.get(2).getAmeId() == submenuVO.getAmeId()) {
                    liClass2 = " class=\"active open\"";
                  }
                }
                
                out.println(String.format("<li%s>%s", liClass2, getMenuLink(request, submenuVO)));
                
                if(chldrns2 && "#".equals(submenuVO.getAmeLinkUrl())) {
                  out.println("<ul>");
                  for(int k = 0; k < sub2menuList.size(); k++) {
                    AdminMenuVO sub2menuVO = (AdminMenuVO) sub2menuList.get(k);
                    
                    String liClass3 = "";
                    if(PARNTS_ADMIN_MENU_LIST.get(3) != null) {
                      if(PARNTS_ADMIN_MENU_LIST.get(3).getAmeId() == sub2menuVO.getAmeId()) {
                        liClass3 = " class=\"active\"";
                      }
                    }
                    
                    out.println(String.format("<li%s>%s</li>", liClass3, getMenuLink(request, sub2menuVO)));
                  }
                  out.println("</ul>");
                }
                out.println("</li>");
              }
              out.println("</ul>");
            }
            out.println("</li>");
          }
        }
      %>
    </ul>
  </div>
  <div class="page-content">
    <div class="container">
      <c:choose>
        <c:when test="${ADMIN_MENU_VO != null}">
          <div class="page-toolbar">
            <div class="page-toolbar-block">
              <div class="page-toolbar-title">
                <%=ADMIN_MENU_VO.getAmeName()%>
              </div>
            </div>
            <ul class="breadcrumb">
              <%
                if(PARNTS_ADMIN_MENU_LIST != null) {
                  for(int z = 0; z < PARNTS_ADMIN_MENU_LIST.size(); z++) {
                    AdminMenuVO vo = (AdminMenuVO) PARNTS_ADMIN_MENU_LIST.get(z);
                    String active = ADMIN_MENU_VO.getAmeId() == vo.getAmeId()
                      ? "class=\"active\""
                      : "";
                    out.println(String.format("<li %s>%s</li>", active, vo.getAmeName()));
                  }
                }
              %>
            </ul>
          </div>
        </c:when>
      </c:choose>
      <div class="content_box"><c:import url="${CONTENT_FILE}"/></div>
    </div>
  </div>
</div>
</div>
<div class="page-sidebar"></div>
</div>
</body>
</html>