package egovframework.coing.cmm;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationRenderer;

import java.text.MessageFormat;

public abstract class AbstractPaginationRenderer implements PaginationRenderer {
    
    public String firstPageLabel;
    public String previousPageLabel;
    public String currentPageLabel;
    public String otherPageLabel;
    public String nextPageLabel;
    public String lastPageLabel;
    
    public String pageStr = "pageIndex";
    
    public String renderPagination(PaginationInfo paginationInfo, String jsFunction) {
        
        pageStr = paginationInfo.getPageStr();
        if(!"".equals(jsFunction)) {
            jsFunction = jsFunction + "&";
        }
        
        StringBuffer strBuff = new StringBuffer();
        
        int firstPageNo = paginationInfo.getFirstPageNo();
        int firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
        int totalPageCount = paginationInfo.getTotalPageCount();
        int pageSize = paginationInfo.getPageSize();
        int lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
        int currentPageNo = paginationInfo.getCurrentPageNo();
        int lastPageNo = paginationInfo.getLastPageNo();
        
        if(totalPageCount > pageSize) {
            if(firstPageNoOnPageList > pageSize) {
                strBuff.append(MessageFormat.format(firstPageLabel, new Object[]{jsFunction, pageStr, Integer.toString(firstPageNo)}));
                strBuff.append(MessageFormat.format(previousPageLabel, new Object[]{jsFunction,
                                                                                    pageStr,
                                                                                    Integer.toString(firstPageNoOnPageList - 1)}));
            } else {
                strBuff.append(MessageFormat.format(firstPageLabel, new Object[]{jsFunction, pageStr, Integer.toString(firstPageNo)}));
                strBuff.append(MessageFormat.format(previousPageLabel, new Object[]{jsFunction, pageStr, Integer.toString(firstPageNo)}));
            }
        }
        
        for(int i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
            if(i == currentPageNo) {
                strBuff.append(MessageFormat.format(currentPageLabel, new Object[]{Integer.toString(i)}));
            } else {
                strBuff.append(MessageFormat.format(otherPageLabel, new Object[]{jsFunction, pageStr, Integer.toString(i), Integer.toString(i)}));
            }
        }
        
        if(totalPageCount > pageSize) {
            if(lastPageNoOnPageList < totalPageCount) {
                strBuff.append(MessageFormat.format(nextPageLabel, new Object[]{jsFunction,
                                                                                pageStr,
                                                                                Integer.toString(firstPageNoOnPageList + pageSize)}));
                strBuff.append(MessageFormat.format(lastPageLabel, new Object[]{jsFunction, pageStr, Integer.toString(lastPageNo)}));
            } else {
                strBuff.append(MessageFormat.format(nextPageLabel, new Object[]{jsFunction, pageStr, Integer.toString(lastPageNo)}));
                strBuff.append(MessageFormat.format(lastPageLabel, new Object[]{jsFunction, pageStr, Integer.toString(lastPageNo)}));
            }
        }
        return strBuff.toString();
    }
    
}