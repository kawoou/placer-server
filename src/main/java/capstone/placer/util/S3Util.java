package capstone.placer.util;

import capstone.placer.config.PropertyLoader;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
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
    private static String S3_URL_PREFIX;
    private String ACCESS_KEY;
    private String PRIVATE_KEY;
    private AmazonS3 s3Connection;

    public S3Util() throws IOException {
        Properties s3Property = PropertyLoader.loadProperties(PROPERTY_FILE_NAME);
        ACCESS_KEY = s3Property.getProperty("cloud.aws.credentials.accessKey");
        PRIVATE_KEY = s3Property.getProperty("cloud.aws.credentials.secretKey");
        S3_URL_PREFIX = s3Property.getProperty("cloud.aws.s3.url");

        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, PRIVATE_KEY);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setProtocol(Protocol.HTTP);
        s3Connection = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.valueOf("AP_NORTHEAST_2")).build();
    }

    public List<Bucket> getBuckets() {
        return s3Connection.listBuckets();
    }

    public void createFolder(String bucketName, String folderName) {
        s3Connection.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
    }

    public void fileUpload(String bucketName, String fileName, byte[] fileData) {
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

    public static String getS3UrlPrefix() {
        return S3_URL_PREFIX;
    }

}
