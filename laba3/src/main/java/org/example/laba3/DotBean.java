package org.example.laba3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("dotBean")
@SessionScoped
public class DotBean implements Serializable {

    private Double x;
    private Double y;
    private Double r;

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setR(Double r) {
        this.r = r;
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

    public static boolean checkX(Double value) {
        return (value <= 5 && value >= -5);
    }
    public static boolean checkY(Double value) {
        return (value <= 5 && value >= -5);
    }
}