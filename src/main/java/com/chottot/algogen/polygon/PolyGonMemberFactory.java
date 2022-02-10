package com.chottot.algogen.polygon;

import com.chottot.algogen.PolygonColor;
import com.chottot.algogen.core.AlgoGenMemberFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PolyGonMemberFactory implements AlgoGenMemberFactory<PolygonMember> {

    private final Random rand;
    private final int width;
    private final int height;
    private final int polygonNumber;
    private final int pointByPolygon;

    public PolyGonMemberFactory(Random rand, int width, int height, int polygonNumber, int pointByPolygon) {
        this.rand = rand;
        this.width = width;
        this.height = height;
        this.polygonNumber = polygonNumber;
        this.pointByPolygon = pointByPolygon;
    }

    public PolyGonMemberFactory(int width, int height, int polygonNumber, int pointByPolygon) {
        this(new Random(), width, height, polygonNumber, pointByPolygon);
    }


    @Override
    public PolygonMember initialize() {
        ArrayList<PolygonColor> list = new ArrayList<>(polygonNumber);

        for (int i = 0; i < polygonNumber; i++) {
            int[] x = new int[pointByPolygon];
            int[] y = new int[pointByPolygon];

            for (int j = 0; j < pointByPolygon; j++) {
                x[j] = rand.nextInt(width*3) - width;
                y[j] = rand.nextInt(height*3) - height;
            }

            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256),  rand.nextInt(256));
            PolygonColor pol = new PolygonColor(x, y, pointByPolygon, color);

            list.add(pol);
        }

        return new PolygonMember(rand, list);
    }
}
