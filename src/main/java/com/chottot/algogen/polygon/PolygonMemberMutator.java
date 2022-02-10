package com.chottot.algogen.polygon;

import com.chottot.algogen.PolygonColor;
import com.chottot.algogen.core.AlgoGenMemberMutator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PolygonMemberMutator implements AlgoGenMemberMutator<PolygonMember> {

    private final Random rand;
    private int mutationStrength;

    public PolygonMemberMutator(Random rand, int mutationStrength) {
        this.rand = rand;
        this.mutationStrength = mutationStrength;
    }

    public PolygonMemberMutator(int mutationStrength) {
        this(new Random(), mutationStrength);
    }

    private int mutateValue(int value){
        return value + rand.nextInt(mutationStrength*2) - mutationStrength;
    }
    private int mutateValue(int value, int min, int max){
        value = mutateValue(value);
        return Math.min( Math.max(value, min), max);
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
                pc.xpoints[poly] = mutateValue(pc.xpoints[poly]);
                pc.ypoints[poly] = mutateValue(pc.ypoints[poly]);
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
