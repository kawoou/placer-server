package capstone.hexgrid;

import java.util.ArrayList;

interface Converter {
    Hex pointToHex(Point p_, int level);
    ArrayList<Hex> pointToHex(Point p_);
}