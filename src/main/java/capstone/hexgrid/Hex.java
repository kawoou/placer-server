package capstone.hexgrid;

import java.util.ArrayList;

public class Hex {
    private int q, r, s;
    private static ArrayList<Hex> hexDirections;

    static {
        hexDirections = new ArrayList<Hex>();
        hexDirections.add(new Hex(1, 0));
        hexDirections.add(new Hex(1, -1));
        hexDirections.add(new Hex(0, -1));
        hexDirections.add(new Hex(-1, 0));
        hexDirections.add(new Hex(-1, 1));
        hexDirections.add(new Hex(0, 1));
    }

    private static Hex add(Hex a, Hex b) {
        return new Hex(a.q() + b.q(), a.r() + b.r(), a.s() + b.s());
    }

    public Hex(int q, int r) {
        this.q = q;
        this.r = r;
        this.s = -q - r;
    }

    public Hex(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;
        assert (q + r + s == 0);
    }

    public int q() {
        return this.q;
    }

    public int r() {
        return this.r;
    }

    public int s() {
        return this.s;
    }

    public boolean equals(Hex x) {
        return this.q == x.q() && r == x.r() && s == x.s();
    }

    private Hex hex_direction(int direction) {
        assert (0 <= direction && direction < 6);
        return hexDirections.get(direction);
    }

    public Hex neighbor(int direction) {
        return Hex.add(this, hex_direction(direction));
    }

    public static Hex hexRound(double q_, double r_, double s_) {
        int qi = (int) Math.round(q_);
        int ri = (int) Math.round(r_);
        int si = (int) Math.round(s_);

        double q_diff = Math.abs(qi - q_);
        double r_diff = Math.abs(ri - r_);
        double s_diff = Math.abs(si - s_);

        if (q_diff > r_diff && q_diff > s_diff) {
            qi = -ri - si;
        } else if (r_diff > s_diff) {
            ri = -qi - si;
        }

        return new Hex(qi, ri);
    }

    public ArrayList<Integer> htol() {
        ArrayList<Integer> coord = new ArrayList<Integer>();
        coord.add(this.q);
        coord.add(this.r);
        coord.add(this.s);
        return coord;
    }
}