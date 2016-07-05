package hu.elte.inetsense.server.upload.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hu.elte.inetsense.common.util.InetsenseUtil;

@RestController
@RequestMapping("/upload")
public class UploadController {


    private final Logger log = LoggerFactory.getLogger(UploadController.class);
    
    @RequestMapping(method = RequestMethod.POST)
    public Long processMessage(@RequestParam("file") MultipartFile file, HttpServletRequest request, 
            HttpServletResponse response) {

        StopWatch stopwatch = (StopWatch) request.getAttribute("stopwatch");
        stopwatch.stop();
        log.info("Upload request took: {}", stopwatch.toString());
        
        return InetsenseUtil.calculateSpeed(file.getSize(), stopwatch.getTime()); 
    }
    
    
}
