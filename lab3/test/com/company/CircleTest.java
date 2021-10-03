package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void getRadius() throws Exception {
        Assertions.assertThrows(Exception.class, () -> {
            Circle circle = new Circle(0, 0, 0);
        });

        Assertions.assertThrows(Exception.class, () -> {
            Circle circle = new Circle(0, 0, Integer.MIN_VALUE);
        });

        Assertions.assertThrows(Exception.class, () -> {
            Circle circle = new Circle(0, 0, -1);
        });

        Circle circle1 = new Circle(0, 0, 1);
        assertEquals(circle1.getRadius(), 1);

        Circle circle2 = new Circle(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(circle2.getRadius(), Integer.MAX_VALUE);
    }

    @Test
    void getCenter() throws Exception {
        Circle circle = new Circle(0, 0, 1);
        Point center = circle.getCenter();
        assertEquals(center.getX(), 0);
        assertEquals(center.getY(), 0);

        Circle circle1 = new Circle(Integer.MIN_VALUE, Integer.MIN_VALUE, 1);
        Point center1 = circle1.getCenter();
        assertEquals(center1.getX(), Integer.MIN_VALUE);
        assertEquals(center1.getY(), Integer.MIN_VALUE);
    }

    @Test
    void setRadius() throws Exception {
        Circle circle = new Circle(10, 15, 123);
        assertEquals(circle.getRadius(), 123);
        Assertions.assertThrows(Exception.class, () -> {
            circle.setRadius(-50);
        });

        assertEquals(circle.getRadius(), 123);

        circle.setRadius(98765);
        assertEquals(circle.getRadius(), 98765);
    }

    @Test
    void setCenter() throws Exception {
        Circle circle = new Circle(10, 15, 1);
        Point center = circle.getCenter();
        assertEquals(center.getX(), 10);
        assertEquals(center.getY(), 15);

        circle.setCenter(-100, 500);
        Point center1 = circle.getCenter();
        assertEquals(center1.getX(), -100);
        assertEquals(center1.getY(), 500);
    }

    @Test
    void getArea() throws Exception {
        Circle circle = new Circle(10, 15, 2);
        assertEquals(circle.getArea(), 4 * Math.PI);
        circle.setRadius(5);
        assertEquals(circle.getArea(), 25 * Math.PI);
    }

    @Test
    void getPerimeter() throws Exception {
        Circle circle = new Circle(10, 15, 3);
        assertEquals(circle.getPerimeter(), 6 * Math.PI);
        circle.setRadius(5);
        assertEquals(circle.getPerimeter(), 10 * Math.PI);
    }
}