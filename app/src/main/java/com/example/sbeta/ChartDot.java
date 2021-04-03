package com.example.sbeta;

/**
 * this is a class to restore the coordinate of results for trails
 */
public class ChartDot {
    private double x;
    private double y;

    public ChartDot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void increamentY() {this.y += 1;}
}
