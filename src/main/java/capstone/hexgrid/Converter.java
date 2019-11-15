package capstone.hexgrid;

import java.util.ArrayList;

public interface Converter {
    Hex pointToHex(Point p_, int level);

    ArrayList<Hex> pointToHex(Point p_);

    int zoomToLevel(double meter);
}