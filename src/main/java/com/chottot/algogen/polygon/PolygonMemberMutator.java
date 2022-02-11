package com.chottot.algogen.polygon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class PolygonMemberMutator implements PolygonMutator {

    private final Random rand;
    private final int mutationStrength;

    private final int width;
    private final int height;

    public PolygonMemberMutator(Random rand, int mutationStrength, int width, int height) {
        this.rand = rand;
        this.mutationStrength = mutationStrength;
        this.width = width;
        this.height = height;
    }

    public PolygonMemberMutator(int mutationStrength, int width, int height) {
        this(new Random(), mutationStrength, width, height);
    }

    private int mutateValue(int value){
        return value + rand.nextInt(mutationStrength*2) - mutationStrength;
    }

    private int mutateValue(int value, int min, int max){
        return rand.nextInt(min, max);
    }


    @Override
    public void mutate(PolygonMember member) {
        ArrayList<PolygonColor> list = member.getList();
        int poly = rand.nextInt(list.size());
        PolygonColor pc = list.get(poly);

        poly = rand.nextInt(8);
        switch (poly){
            case 0,1,2,3 -> {
                poly = rand.nextInt(pc.npoints);
                pc.xpoints[poly] = mutateValue(pc.xpoints[poly], -width, width*2);
                pc.ypoints[poly] = mutateValue(pc.ypoints[poly], -height, height*2);
            }
            case 4 -> {
                Color c = pc.getColor();
                pc.setColor( new Color( mutateValue(c.getRed(), 0, 255), c.getGreen(), c.getBlue(), c.getAlpha()) );
            }
            case 5 -> {
                Color c = pc.getColor();
                pc.setColor( new Color( c.getRed(), mutateValue(c.getGreen(), 0, 255), c.getBlue(), c.getAlpha()) );
            }
            case 6 -> {
                Color c = pc.getColor();
                pc.setColor( new Color( c.getRed(), c.getGreen(), mutateValue(c.getBlue(), 0, 255), c.getAlpha()) );
            }
            case 7 -> {
                Color c = pc.getColor();
                pc.setColor( new Color( c.getRed(), c.getGreen(), c.getBlue(), mutateValue(c.getAlpha(), 0, 255)) );
            }
            default -> {}
        }
    }
}
