package capstone.hexgrid;

import java.util.ArrayList;

public class CoordHandler {
    private ArrayList<GPSConverter> converters;

    public CoordHandler() {
        double size = 0.05;
        for (int i = 0; i < 5; i++) {
            this.converters.add(new GPSConverter(size));
            size *= 2.0;
        }
    }

    public ArrayList<Hex> convert(Point p_) {
        ArrayList<Hex> results = new ArrayList<Hex>();
        for (GPSConverter c : this.converters) {
            Hex result = c.pointToHex(p_);
            results.add(result);
        }
        return results;
    }

    public Hex convert(int level, Point p_) {
        return this.converters.get(level).pointToHex(p_);
    }
}
