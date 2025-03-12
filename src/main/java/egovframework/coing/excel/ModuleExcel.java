package egovframework.coing.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ModuleExcel<T> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleExcel.class);
    
    private static final int ROW_START_INDEX = 0;
    private static final int COLUMN_START_INDEX = 0;
    
    private SXSSFWorkbook workbook;
    private Sheet sheet;
    
    private Class<? extends Object> type;
    
    public ModuleExcel() {
    }
    
    public ModuleExcel(List<? extends Object> list, Class<? extends Object> type) {
        LOGGER.debug("::: CREATE EXCEL OBJECT :::");
        this.workbook = new SXSSFWorkbook();
        this.type = type;
        createModuleExelWorkbook(list);
    }
    
    private void createModuleExelWorkbook(List<? extends Object> list) {
        this.sheet = this.workbook.createSheet();
        createWorkbookHeader(sheet, ROW_START_INDEX, COLUMN_START_INDEX);
        
        int rowIndex = ROW_START_INDEX + 1;
        for(Object rowData : list) {
            createWorkbookBody(rowData, rowIndex++, COLUMN_START_INDEX);
        }
    }
    
    private void createWorkbookHeader(Sheet sheet, int rowIndex, int colStartIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = colStartIndex;
        LOGGER.debug("========= HEADER =========");
        for(String dataFieldName : getFieldNames()) {
            LOGGER.debug("header name ::: " + dataFieldName);
            Cell cell = row.createCell(columnIndex++);
            cell.setCellValue(dataFieldName);
        }
    }
    
    private void createWorkbookBody(Object rowData, int rowIndex, int colStartIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = colStartIndex;
        LOGGER.debug("========= BODY =========");
        for(String dataFieldName : getFieldNames()) {
            LOGGER.debug("body name ::: " + dataFieldName);
            Cell cell = row.createCell(columnIndex++);
            try {
                Field field = getField(rowData.getClass(), dataFieldName);
                field.setAccessible(true);
                createCellValue(cell, field.get(rowData));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private List<String> getFieldNames() {
        Field[] parentFields = this.type.getSuperclass().getDeclaredFields();
        Field[] fields = this.type.getDeclaredFields();
        List<String> fieldNameList = new ArrayList<>();
        
        for(int i = 0; i < parentFields.length; i++) {
            if(parentFields[i].isAnnotationPresent(ExcelColumn.class)) {
                fieldNameList.add(parentFields[i].getAnnotation(ExcelColumn.class).headerName());
            }
        }
        
        for(int i = 0; i < fields.length; i++) {
            if(fields[i].isAnnotationPresent(ExcelColumn.class)) {
                fieldNameList.add(fields[i].getAnnotation(ExcelColumn.class).headerName());
            }
        }
        return fieldNameList;
    }
    
    private Field getField(Class<? extends Object> dataType, String dataFieldName) {
        
        Field[] parentFields = dataType.getSuperclass().getDeclaredFields();
        Field[] fields = dataType.getDeclaredFields();
        
        for(int i = 0; i < parentFields.length; i++) {
            if(parentFields[i].isAnnotationPresent(ExcelColumn.class) && dataFieldName.equals(parentFields[i].getAnnotation(ExcelColumn.class)
                    .headerName())) {
                return parentFields[i];
            }
        }
        
        for(int i = 0; i < fields.length; i++) {
            if(fields[i].isAnnotationPresent(ExcelColumn.class) && dataFieldName.equals(fields[i].getAnnotation(ExcelColumn.class).headerName())) {
                return fields[i];
            }
        }
        
        return null;
    }
    
    private void createCellValue(Cell cell, Object cellValue) {
        if(cellValue instanceof Number) {
            Number numberValue = (Number) cellValue;
            cell.setCellValue(numberValue.doubleValue());
            return;
        }
        if(cellValue == null) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(cellValue.toString());
        }
    }
    
    public void write(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.setDisposition(fileName, request, response);
        
        OutputStream stream;
        try {
            stream = response.getOutputStream();
            workbook.write(stream);
        } catch(IOException e) {
            System.out.println("write error : " + e.getMessage());
            throw new IOException();
            
        } finally {
            workbook.close();
            workbook.dispose();
        }
        stream.close();
        
    }
    
    private String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if(header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if(header.indexOf("Trident") > -1) {
            return "Trident";
        } else if(header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if(header.indexOf("Opera") > -1) {
            return "Opera";
        }
        return "Firefox";
    }
    
    private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String browser = getBrowser(request);
        
        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;
        
        if(browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if(browser.equals("Trident")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if(browser.equals("Firefox")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if(browser.equals("Opera")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if(browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if(c > '~') {
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
        
        if("Opera".equals(browser)) {
            response.setContentType("application/octet-stream;charset=UTF-8");
        }
    }
}