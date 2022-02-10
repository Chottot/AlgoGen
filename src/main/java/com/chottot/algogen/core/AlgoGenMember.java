package com.chottot.algogen.core;

import java.awt.*;
import java.util.Random;

public abstract class AlgoGenMember{

    public double fitness;
    public double pickRate;
    protected Random rand;

    private final AlgoGenMember parent1;
    private final AlgoGenMember parent2;

    public AlgoGenMember(Random rand, AlgoGenMember parent1, AlgoGenMember parent2) {
        this.rand = rand;
        this.parent1 = parent1;
        this.parent2 = parent2;
        fitness = 0;
        pickRate = 0;
    }

    public AlgoGenMember(Random rand) {
        this(rand, null, null);
    }

    public abstract void render(Graphics2D g, int width, int height);


    public AlgoGenMember getParent1() {
        return parent1;
    }

    public AlgoGenMember getParent2() {
        return parent2;
    }
}
