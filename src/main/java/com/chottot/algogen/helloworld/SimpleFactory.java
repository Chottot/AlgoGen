package com.chottot.algogen.helloworld;

import java.util.Random;

public class SimpleFactory implements Factory{

    private Random rand;
    private int size;

    public SimpleFactory(Random rand, int size) {
        this.rand = rand;
        this.size = size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    @Override
    public StringMember initialize() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append( (char)rand.nextInt(32,127));
        }
        return new StringMember(rand, builder.toString());
    }
}
