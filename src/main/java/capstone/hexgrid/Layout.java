package capstone.hexgrid;

public class Layout { // Flat Layout only implementation - can be expanded by making abstract class and write flat & pointy layout class
    protected final Orientation or;
    protected final Point size;
    protected final Point origin;
    protected Layout(Point size_, Point origin_) {
        or = new Orientation(3.0 / 2.0, 0.0, StrictMath.sqrt(3.0) / 2.0, StrictMath.sqrt(3.0), 2.0 / 3.0, 0.0, -1.0 / 3.0, StrictMath.sqrt(3.0) / 3.0, 0.0);
        size = size_;
        origin = origin_;
    }
}

class Orientation {
    double f0, f1, f2, f3;
    double b0, b1, b2, b3;

    double start_angle;

    protected Orientation(double f0_, double f1_, double f2_, double f3_,
                          double b0_, double b1_, double b2_, double b3_,
                          double start_angle_) {
        this.f0 = f0_;
        this.f1 = f1_;
        this.f2 = f2_;
        this.f3 = f3_;
        this.b0 = b0_;
        this.b1 = b1_;
        this.b2 = b2_;
        this.b3 = b3_;
        this.start_angle = start_angle_;
    }
}
