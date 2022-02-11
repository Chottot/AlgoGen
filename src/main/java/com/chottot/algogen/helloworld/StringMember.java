package com.chottot.algogen.helloworld;

import com.chottot.algogen.core.AlgoGenMember;

import java.awt.*;
import java.util.Random;

public class StringMember extends AlgoGenMember {

    private String str;

    public StringMember(Random rand, AlgoGenMember parent1, AlgoGenMember parent2, String str) {
        super(rand, parent1, parent2);
        this.str = str;
    }

    public StringMember(Random rand, String str) {
        super(rand);
        this.str = str;
    }

    public StringMember(Random rand, AlgoGenMember parent1, AlgoGenMember parent2) {
        super(rand, parent1, parent2);
    }

    public StringMember(Random rand) {
        super(rand);
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public void render(Graphics2D g, int width, int height) {

    }
}
