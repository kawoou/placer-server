package capstone.placer.exif;

import com.drew.metadata.Tag;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Exif {
    private double aperture;
    private double focalLength;
    private int exposureTime;
    private int iso;
    private boolean flash;
    private String manufacturer;
    private String lensModel;
    private String timestamp;

    public Exif(ArrayList<Tag> tags) {
        for (Tag t : tags) {
            if (t.getTagName().equals("Aperture Value")) {
                this.aperture = Double.parseDouble(t.getDescription().split("/")[1]);
            } else if (t.getTagName().equals("Focal Length")) {
                this.focalLength = Double.parseDouble(t.getDescription().split(" ")[0]);
            } else if (t.getTagName().equals("Exposure Time")) {
                this.exposureTime = Integer.parseInt(t.getDescription().split("/| ")[1]);
            } else if (t.getTagName().equals("Exposure Time")) {
                this.iso = Integer.parseInt(t.getDescription().split("/| ")[1]);
            } else if (t.getTagName().equals("Exposure Time")) {
                if (t.getDescription().contains("not")) {
                    this.flash = false;
                } else {
                    this.flash = true;
                }
            } else if (t.getTagName().equals("Lens Make")) {
                this.manufacturer = t.getDescription();
            } else if (t.getTagName().equals("Lens Model")) {
                this.lensModel = t.getDescription();
            } else if (t.getTagName().equals("Date/Time Original")) {
                this.timestamp = t.getDescription();
            }
        }
    }
}
