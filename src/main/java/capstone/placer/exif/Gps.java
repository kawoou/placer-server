package capstone.placer.exif;

import java.util.ArrayList;

import capstone.hexgrid.Point;
import com.drew.metadata.Tag;

public class Gps {
    double longitude;
    double latitude;
    double altitude;

    public Gps(ArrayList<Tag> tags) {
        for (Tag t : tags) {
            if (t.getTagName().equals("GPS Longitude")) {
                this.longitude = Gps.parsetoDouble(t.getDescription());
            } else if (t.getTagName().equals("GPS Latitude")) {
                this.latitude = Gps.parsetoDouble(t.getDescription());
            } else if (t.getTagName().equals("GPS Altitude")) {
                this.altitude = Gps.parsetoDouble(t.getDescription());
            }
        }
    }

    private static double parsetoDouble(String val) {
        String digits = String.join("", val.split("\"|\'| |\\."));
        String doubleformed = digits.replace('°', '.');
        double result = Double.parseDouble(doubleformed);
        return result;
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
