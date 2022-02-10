package com.chottot.algogen;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class PolygonColor extends Polygon {

    private Color color;

    public PolygonColor(int[] xpoints, int[] ypoints, int npoints, Color color) {
        super(xpoints, ypoints, npoints);
        this.color = color;
    }

    public PolygonColor(Polygon poly,  Color color) {
        this(poly.xpoints, poly.ypoints, poly.npoints, color);
    }

    public PolygonColor(PolygonColor poly) {
        this(poly.xpoints, poly.ypoints, poly.npoints, poly.getColor());
    }

    public PolygonColor(int[] xpoints, int[] ypoints, int npoints) {
        this(xpoints, ypoints, npoints, Color.BLACK);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static PolygonColor getRandom(int width, int height, int polygonPointNumber){
        Random rand = new Random();
        PolygonColor p;

        int[] x = new int[polygonPointNumber];
        int[] y = new int[polygonPointNumber];

        for (int j = 0; j < polygonPointNumber; j++) {
            x[j] = rand.nextInt(width);
            y[j] = rand.nextInt(height);
        }

        Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        return new PolygonColor(x, y, polygonPointNumber, color);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("PolygonColor{ color= " + color);
        for (int i = 0; i < this.npoints; i++) {
            builder.append(" [ ").append(this.xpoints[i]).append(", ").append(this.ypoints[i]).append(" ]");
        }

        return builder.toString();
    }
}
