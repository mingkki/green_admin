package egovframework.coing.cmm;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EgovComOthersExcepHndlr implements ExceptionHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EgovComOthersExcepHndlr.class);
    
    public void occur(Exception exception, String packageName) {
        //log.debug(" EgovServiceExceptionHandler run...............");
        LOGGER.error(packageName, exception);
    }
}