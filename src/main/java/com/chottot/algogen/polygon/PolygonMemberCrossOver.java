package com.chottot.algogen.polygon;

import com.chottot.algogen.PolygonColor;
import com.chottot.algogen.core.AlgoGenMemberCrossOver;

import java.util.ArrayList;
import java.util.Random;

public class PolygonMemberCrossOver implements AlgoGenMemberCrossOver<PolygonMember> {

    private final Random rand;

    public PolygonMemberCrossOver(Random rand) {
        this.rand = rand;
    }

    public PolygonMemberCrossOver() {
        rand = new Random();
    }

    @Override
    public PolygonMember crossOver(PolygonMember parent1, PolygonMember parent2) {
        ArrayList<PolygonColor> list = new ArrayList<>(parent1.getPolygonNumber());

        for (int i = 0; i < parent1.getPolygonNumber(); i++) {
            PolygonColor pc1 = parent1.getPolygonColor(i);
            PolygonColor pc2 = parent2.getPolygonColor(i);

            switch (rand.nextInt(4)){
                case 0 -> list.add( new PolygonColor(pc1, pc2.getColor()));
                case 1 -> list.add( new PolygonColor(pc2, pc1.getColor()));
                case 2 -> list.add( new PolygonColor(pc1));
                case 3 -> list.add( new PolygonColor(pc2));
            }

        }

        return new PolygonMember(rand, parent1, parent2, list);
    }
}
