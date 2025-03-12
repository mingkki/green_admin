package egovframework.coing.cmm.web;

import egovframework.coing.cmm.Globals;
import egovframework.coing.cmm.util.EgovResourceCloseHelper;
import egovframework.coing.cmm.util.EgovWebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class ImageViewController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageViewController.class);
    
    @RequestMapping("/imgview.do")
    public void imgview(@RequestParam(value = "file", required = true) String file,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        
        String downFileName = Globals.DISTRIBUTE_UPLOAD_PATH + file.replace(Globals.DISTRIBUTE_UPLOAD_DIR, "/");
        File f = new File(EgovWebUtil.filePathBlackList(downFileName));
        FileInputStream fis = null;
        BufferedInputStream in = null;
        ByteArrayOutputStream bStream = null;
        try {
            fis = new FileInputStream(f);
            in = new BufferedInputStream(fis);
            bStream = new ByteArrayOutputStream();
            int imgByte;
            while((imgByte = in.read()) != -1) {
                bStream.write(imgByte);
            }
            int pos = file.lastIndexOf(".");
            String ext = file.substring(pos + 1);
            String type = "";
            if(ext != null && !"".equals(ext)) {
                if("jpg".equals(ext.toLowerCase())) {
                    type = "image/jpeg";
                } else {
                    type = "image/" + ext.toLowerCase();
                }
                type = "image/" + ext.toLowerCase();
            } else {
                LOGGER.debug("Image fileType is null.");
            }
            response.setHeader("Contenst-Type", type);
            response.setContentLength(bStream.size());
            bStream.writeTo(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch(FileNotFoundException e) {
            response.sendError(404);
        } finally {
            EgovResourceCloseHelper.close(bStream, in, fis);
        }
    }
}