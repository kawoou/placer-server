package capstone.hexgrid;

import java.util.ArrayList;
import java.lang.StrictMath;

public class GPSConverter implements Converter {
    private static double center_lat = 36.370748;
    private static double center_long = 127.973369;
    private static final double rate = 110.574 / (111.320 * Math.cos(center_lat * Math.PI / 180));

    public static final int levels = 8;
    private static final double[] size = {0.01, 0.025, 0.07, 0.16, 0.4, 1, 2.5, 6.25};
    private static final Layout[] layout = new Layout[GPSConverter.levels];

    private static final double meter_ratio = 0.0000091;

    public GPSConverter() {
        Point center = new Point(center_long, center_lat);

        for (int i = 0; i < GPSConverter.levels; i++) {
            layout[i] = new Layout(new Point(rate * GPSConverter.size[i], GPSConverter.size[i]), center);
        }
    }

    public Hex pointToHex(Point p_, int level) {
        Layout l = GPSConverter.layout[level];
        Point p = new Point((p_.x - l.origin.x) / l.size.x, (p_.y - l.origin.y) / l.size.y);
        double q = l.or.b0 * p.x + l.or.b1 * p.y;
        double r = l.or.b2 * p.x + l.or.b3 * p.y;

        return Hex.hexRound(q, r, -q - r);
    }

    public ArrayList<Hex> pointToHex(Point p_) {
        ArrayList<Hex> results = new ArrayList<Hex>();
        for (Layout l : GPSConverter.layout) {
            Point p = new Point((p_.x - l.origin.x) / l.size.x, (p_.y - l.origin.y) / l.size.y);
            double q = l.or.b0 * p.x + l.or.b1 * p.y;
            double r = l.or.b2 * p.x + l.or.b3 * p.y;
            results.add(Hex.hexRound(q, r, -q - r));
        }
        return results;
    }

    public int zoomToLevel(double meter) {
        double view_range = meter * GPSConverter.meter_ratio * StrictMath.sqrt(2.0) / 2;
        int lev = -1;

        for (int i = 0; i < GPSConverter.levels; i++) {
            if (view_range < GPSConverter.size[i]) {
                lev = i;
                break;
            }
        }
        if (lev == -1) {
            return GPSConverter.levels - 1;
        } else {
            return lev;
        }
    }
}