package capstone.placer.util;

import capstone.placer.config.PropertyLoader;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class S3Util {

    private static final String PROPERTY_FILE_NAME = "s3.properties";
    private String ACCESS_KEY;
    private String PRIVATE_KEY;
    private AmazonS3 s3Connection;

    public S3Util() throws IOException {
        Properties s3Property = PropertyLoader.loadProperties(PROPERTY_FILE_NAME);
        ACCESS_KEY = s3Property.getProperty("cloud.aws.credentials.accessKey");
        PRIVATE_KEY = s3Property.getProperty("cloud.aws.credentials.secretKey");

        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, PRIVATE_KEY);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setProtocol(Protocol.HTTP);
        s3Connection = new AmazonS3Client(credentials, conf);
        s3Connection.setEndpoint("s3.ap-northeast-2.amazonaws.com");
    }

    public List<Bucket> getBuckets() {
        return s3Connection.listBuckets();
    }

    public void createFolder(String bucketName, String folderName) {
        s3Connection.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
    }

    public void fileUpload(String bucketName, String fileName, byte[] fileData) throws FileNotFoundException {
        String filePath = fileName.replace(File.separatorChar, '/');
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(fileData.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);

        s3Connection.putObject(bucketName, filePath, byteArrayInputStream, metadata);
    }

    public String getFileURL(String bucketName, String fileName) {
        String imgName = fileName.replace(File.separatorChar, '/');
        return s3Connection.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, imgName)).toString();
    }

}
