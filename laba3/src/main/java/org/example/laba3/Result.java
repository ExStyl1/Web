package org.example.laba3;

public class Result {
    private Double x;
    private Double y;
    private Double r;
    private boolean hit;

    public boolean isHit() {
        return hit;
    }


    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getR() {
        return r;
    }


    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setR(Double r) {
        this.r = r;
    }


    @Override
    public String toString() {
        return "Result{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", isHit=" + hit +
                '}';
    }

    public Result(Double x, Double y, Double r, boolean hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }

    public static boolean dotIsHit(double x, double y, double r) {
        double halfR = r / 2.0;
        return (((x >= 0 && x <= r && y >= 0 && y <= halfR || x >= -r && x <= 0 && y >= -r && y <= 0 && y >= - r - x || x >= 0 && x <= halfR && y >= -1 * halfR && y <= 0 && (x * x + y * y <= (halfR) * (halfR)))));
    }


}
