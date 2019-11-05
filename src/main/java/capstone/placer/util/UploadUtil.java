package capstone.placer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

public class UploadUtil {
    private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);

    public static String uploadFile(String uploadPath, String originalName, byte[] byteData) throws Exception {
        S3Util s3 = new S3Util();
        String bucketName = "placer.imgserver";
        UUID uid = UUID.randomUUID();

        String savedName = "/" + uid.toString() + "_" + originalName;

        logger.info("업로드 경로 : " + uploadPath);

        String savedPath = calcPath(uploadPath);

        String uploadedFileName = (savedPath + savedName).replace(File.separatorChar, '/');

        s3.fileUpload(bucketName, uploadPath + uploadedFileName, byteData);

        logger.info(uploadedFileName);

        return uploadedFileName;
    }

    private static String calcPath(String uploadPath) {
        Calendar cal = Calendar.getInstance();

        String yearPath = File.separator + cal.get(Calendar.YEAR);

        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

        String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

        makeDir(uploadPath, yearPath, monthPath, datePath);

        logger.info(datePath);

        return datePath;
    }

    private static void makeDir(String uploadPath, String... paths) {

        if (new File(paths[paths.length - 1]).exists()) {
            return;
        }

        for (String path : paths) {

            File dirPath = new File(uploadPath + path);

            if (!dirPath.exists()) {
                dirPath.mkdir();
            }
        }
    }

}
