package egovframework.coing.cmm;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public class PaginationRendererDefault extends AbstractPaginationRenderer implements ServletContextAware {
    
    public PaginationRendererDefault() {
        // no-op
    }
    
    public void initVariables() {
        firstPageLabel = "<li><a href=\"?{0}{1}={2}\">first</a></li>";
        previousPageLabel = "<li><a href=\"?{0}{1}={2}\">prev</a></li>";
        currentPageLabel = "<li class=\"active\"><a href=\"#\"><strong>{0}</strong></a></li>";
        otherPageLabel = "<li><a href=\"?{0}{1}={2}\">{3}</a></li>";
        nextPageLabel = "<li><a href=\"?{0}{1}={2}\">next</a></li>";
        lastPageLabel = "<li><a href=\"?{0}{1}={2}\">last</a></li>";
    }
    
    @Override
    public void setServletContext(ServletContext servletContext) {
        initVariables();
    }
    
}