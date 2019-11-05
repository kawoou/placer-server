package capstone.placer.exif;

import java.io.IOException;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class Extractor {
    public ArrayList<Tag> extract(byte[] file) throws IOException, JpegProcessingException {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        ByteArrayInputStream bis = new ByteArrayInputStream(file);
        try {
            Metadata metadata = JpegMetadataReader.readMetadata(bis);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    tags.add(tag);
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }
        } catch (IOException | JpegProcessingException e) {
            throw e;
        } finally {
            bis.close();
        }
        return tags;
    }
}
