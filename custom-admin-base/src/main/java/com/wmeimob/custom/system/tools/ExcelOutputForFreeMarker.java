package com.wmeimob.custom.system.tools;

import com.wmeimob.tool.RandomCodeUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by Shinez on 2017/1/12.
 */
@org.springframework.context.annotation.Configuration
public class ExcelOutputForFreeMarker {

    private static final Logger logger = LoggerFactory.getLogger(ExcelOutputForFreeMarker.class);

    //    private static Configuration cfg;
    private static Template dateTmp;

    private static byte flag = 0;

    private static FreeMarkerConfigurer fmc;

    @Resource
    public void initFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        fmc = freeMarkerConfigurer;
    }
//
//    static {
//        cfg = new Configuration(Configuration.VERSION_2_3_22);
//        try {
//            cfg.setDirectoryForTemplateLoading(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "templates"));
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        }
//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setTemplateUpdateDelayMilliseconds(0);
//        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//    }

    public static boolean output(Map<String, Object> dataMap, String ftlPath, String downloadName, HttpServletResponse response) throws IOException, TemplateException {
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        boolean result = false;
        fmc.setDefaultEncoding("UTF-8");
        fmc.getConfiguration().setClassForTemplateLoading(ExcelOutputForFreeMarker.class, "/templates");
        dateTmp = fmc.getConfiguration().getTemplate(ftlPath);
        File file = new File("tmp");
        file.mkdirs();
        file = new File("tmp/" + System.currentTimeMillis() + (++flag) + ".xls");
        Writer out = null;
        OutputStream os = null;
        FileInputStream fis = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            dateTmp.process(dataMap, out);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + downloadName);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            os = response.getOutputStream();
            fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = fis.read(buf)) > 0) {

                os.write(buf, 0, len);
            }
            return true;
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
            if (fis != null) {
                fis.close();
            }
            if (out != null) {

                out.flush();
                out.close();
            }
            file.delete();
        }
    }


    public static void main(String[] args) {
        System.out.println(RandomCodeUtil.randCode(32));
    }
}
