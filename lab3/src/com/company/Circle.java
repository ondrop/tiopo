package com.company;

import java.awt.*;

public class Circle {

    private int radius;
    private Point center;

    public Circle(int x, int y, int circleRadius) throws Exception {
        center = new Point(x, y);
        setRadius(circleRadius);
    }

    public int getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setRadius(int newRadius) throws Exception {
        if (newRadius <= 0) {
            throw new Exception("Radius cannot be low than 1");
        }

        radius = newRadius;
    }

    public void setCenter(int x, int y) {
        center = new Point(x, y);
    }

    public double getArea() {
        return Math.PI * Math.pow(getRadius(), 2);
    }

    public double getPerimeter() {
        return 2 * Math.PI * getRadius();
    }
}
