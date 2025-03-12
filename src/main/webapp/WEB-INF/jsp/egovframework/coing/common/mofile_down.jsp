<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="org.springframework.util.FileCopyUtils" %>
<%@ page import="egovframework.coing.board.vo.BoardFileVO" %>
<%!
public String getBrowser(HttpServletRequest request) {
    String header = request.getHeader("User-Agent");
    if (header.indexOf("MSIE") > -1) {
        return "MSIE";
    } else if (header.indexOf("Trident") > -1) {
        return "Trident";
    } else if (header.indexOf("Chrome") > -1) {
        return "Chrome";
    } else if (header.indexOf("Opera") > -1) {
        return "Opera";
    }
    return "Firefox";
}

 public void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	String browser = getBrowser(request);

	String dispositionPrefix = "attachment; filename=";
	String encodedFilename = null;

	if (browser.equals("MSIE")) {
		encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
	} else if (browser.equals("Trident")) {
		encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
	} else if (browser.equals("Firefox")) {
		encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
	} else if (browser.equals("Opera")) {
		encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
	} else if (browser.equals("Chrome")) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < filename.length(); i++) {
			char c = filename.charAt(i);
			if (c > '~') {
				sb.append(URLEncoder.encode("" + c, "UTF-8"));
			} else {
				sb.append(c);
			}
		}
		encodedFilename = sb.toString();
	} else {
		throw new IOException("Not supported browser");
	}

	response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

	if ("Opera".equals(browser)){
		response.setContentType("application/octet-stream;charset=UTF-8");
	}
}

%>
<%
File uFile = (File)request.getAttribute("downloadFile");
String filename = request.getAttribute("filename").toString();
String contentType = request.getAttribute("contentType").toString();

int fSize = (int)uFile.length();

if (fSize > 0) {
	String mimetype = "application/x-msdownload";
	
	response.setContentType(mimetype);
	setDisposition(filename, request, response);
	response.setContentLength(fSize);
	
	BufferedInputStream in = null;
	BufferedOutputStream out2 = null;
	
	out.clear();
	
	try {
		
	    in = new BufferedInputStream(new FileInputStream(uFile));
	    out2 = new BufferedOutputStream(response.getOutputStream());
	
	    FileCopyUtils.copy(in, out2);
	    out2.flush();
	    
	} catch (Exception ex) {
		
	} finally {
		
	    if (in != null) {
			try {
			    in.close();
			} catch (Exception ignore) {
				//LOGGER.debug("IGNORED: {}", ignore.getMessage());
			}
	    }
	    if (out2 != null) {
			try {
				out2.close();
			} catch (Exception ignore) {
				//LOGGER.debug("IGNORED: {}", ignore.getMessage());
			}
	    }
	}
}
%>