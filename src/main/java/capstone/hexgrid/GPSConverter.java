package capstone.hexgrid;

class GPSConverter implements Converter {
    private static double center_lat = 36.370748;
    private static double center_long = 127.973369;
    private static final double rate = 110.574 / (111.320 * Math.cos(center_lat * Math.PI / 180));
    protected Layout layout;

    public GPSConverter() {
        Point center = new Point(center_long, center_lat);
        this.layout = new Layout(new Point(rate * 0.001, 0.001), center);
    }

    public GPSConverter(double size) {
        Point center = new Point(center_long, center_lat);
        this.layout = new Layout(new Point(rate * size, size), center);
    }

    public GPSConverter(Layout l_) {
        this.layout = l_;
    }


    public Hex pointToHex(Point p_) {
        Point p = new Point((p_.x - this.layout.origin.x) / this.layout.size.x, (p_.y - this.layout.origin.y) / this.layout.size.y);
        double q = this.layout.or.b0 * p.x + this.layout.or.b1 * p.y;
        double r = this.layout.or.b2 * p.x + this.layout.or.b3 * p.y;

        return Hex.hexRound(q, r, -q - r);
    }
}