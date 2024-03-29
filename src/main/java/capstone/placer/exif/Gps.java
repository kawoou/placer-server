package capstone.placer.exif;

import java.util.ArrayList;

import capstone.hexgrid.Point;
import com.drew.metadata.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Gps {
    private double longitude;
    private double latitude;
    private double altitude;

    public Gps(ArrayList<Tag> tags) throws IllegalArgumentException {
        if (tags.isEmpty()) {
            throw new IllegalArgumentException();
        }
        boolean long_flag = false;
        boolean lat_flag = false;

        for (Tag t : tags) {
            switch (t.getTagName()) {
                case "GPS Longitude":
                    System.out.println(t);
                    this.longitude = Gps.parsetoDouble(t.getDescription());
                    long_flag = true;
                    break;
                case "GPS Latitude":
                    System.out.println(t);
                    this.latitude = Gps.parsetoDouble(t.getDescription());
                    lat_flag = true;
                    break;
                case "GPS Altitude":
                    System.out.println(t);
                    this.altitude = Gps.parsealt(t.getDescription());
                    break;
            }
        }
        if (!(long_flag && lat_flag)) {
            throw new IllegalArgumentException();
        }
    }

    private static double parsetoDouble(String val) {
        String digits = String.join("", val.split("\"|\'| |\\."));
        String doubleformed = digits.replace('°', '.');
        return Double.parseDouble(doubleformed);
    }

    private static double parsealt(String val) {
        String digits = String.join("", val.split("metres"));
        return Double.parseDouble(digits);
    }

    public Point asPoint() {
        return new Point(this.longitude, this.latitude);
    }

    public double getAlt() {
        return this.altitude;
    }

    public double getLong() {
        return this.longitude;
    }

    public double getLat() {
        return this.latitude;
    }
}
