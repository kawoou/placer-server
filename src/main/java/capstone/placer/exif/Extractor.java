package capstone.placer.exif;

import java.io.IOException;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;

import capstone.placer.post.PostDetail;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class Extractor {
    public Exif exifExtract(byte[] file) {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        ByteArrayInputStream bis = new ByteArrayInputStream(file);
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(bis);
            for (Directory directory : metadata.getDirectoriesOfType(ExifSubIFDDirectory.class)) {
                for (Tag tag : directory.getTags()) {
                    tags.add(tag);
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }
            bis.close();
        } catch (IOException | ImageProcessingException e) {
            System.err.println(e);
        }
        return new Exif(tags);
    }

    public Gps GPSExtract(byte[] file) {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        ByteArrayInputStream bis = new ByteArrayInputStream(file);
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(bis);
            for (Directory directory : metadata.getDirectoriesOfType(GpsDirectory.class)) {
                for (Tag tag : directory.getTags()) {
                    tags.add(tag);
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }
            bis.close();
        } catch (IOException | ImageProcessingException e) {
            System.err.println(e);
        }
        return new Gps(tags);
    }

    public PostDetail toPostDetail(long id, Exif e, Gps g) {
        return new PostDetail(id, e.aperture, e.focalLength, e.exposureTime, e.iso, e.flash, e.manufacturer, e.lensModel, g.longitude, g.latitude, g.altitude, e.timestamp);
    }

    public PostDetail Extract(long id, byte[] file) {
        Exif exif = this.exifExtract(file);
        Gps gps = this.GPSExtract(file);
        return this.toPostDetail(id, exif, gps);
    }
}
