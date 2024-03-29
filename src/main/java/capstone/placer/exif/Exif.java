package capstone.placer.exif;

import com.drew.metadata.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Exif {
    private Double aperture;
    private Double focalLength;
    private Integer exposureTime;
    private Integer iso;
    private Boolean flash;
    private String manufacturer;
    private String lensModel;

    @NotNull
    private String timestamp;

    public Exif(ArrayList<Tag> tags) throws IllegalArgumentException {
        if (tags.isEmpty()) {
            throw new IllegalArgumentException();
        }

        boolean timestampFlag = false;

        for (Tag t : tags) {
            if (t.getTagName().equals("Aperture Value")) {
                System.out.println(t);
                this.aperture = Double.parseDouble(t.getDescription().split("/")[1]);
            } else if (t.getTagName().equals("Focal Length")) {
                System.out.println(t);
                this.focalLength = Double.parseDouble(t.getDescription().split(" ")[0]);
            } else if (t.getTagName().equals("Exposure Time")) {
                System.out.println(t);
                this.exposureTime = Integer.parseInt(t.getDescription().split("/| ")[1]);
            } else if (t.getTagName().equals("ISO Speed Ratings")) {
                System.out.println(t);
                this.iso = Integer.parseInt(t.getDescription());
            } else if (t.getTagName().equals("Flash")) {
                System.out.println(t);
                if (t.getDescription().contains("not")) {
                    this.flash = false;
                } else {
                    this.flash = true;
                }
            } else if (t.getTagName().equals("Lens Make")) {
                System.out.println(t);
                this.manufacturer = t.getDescription();
            } else if (t.getTagName().equals("Lens Model")) {
                System.out.println(t);
                this.lensModel = t.getDescription();
            } else if (t.getTagName().equals("Date/Time Original")) {
                System.out.println(t);
                timestampFlag = true;
                this.timestamp = t.getDescription();
            }
        }

        if (!timestampFlag) {
            throw new IllegalArgumentException();
        }
    }
}
