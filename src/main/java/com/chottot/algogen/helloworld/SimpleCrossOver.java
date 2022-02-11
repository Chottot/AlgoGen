package com.chottot.algogen.helloworld;

import java.util.Random;

public class SimpleCrossOver implements CrossOver{

    private Random rand;

    public SimpleCrossOver(Random rand) {
        this.rand = rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    @Override
    public StringMember crossOver(StringMember parent1, StringMember parent2) {
        StringBuilder builder = new StringBuilder();

        if(parent1.getStr().equals( parent2.getStr())){
            System.out.println("SAME PARENT STR: " + parent1.hashCode() + " / " + parent2.hashCode() + "   str: " + parent1.getStr());
        }

        for (int i = 0; i < parent1.getStr().length(); i++) {
            if(rand.nextBoolean()){
                builder.append(parent1.getStr().charAt(i));
            }else{
                builder.append(parent2.getStr().charAt(i));
            }
        }

        StringMember res = new StringMember(rand, parent1, parent2, builder.toString());
        //System.out.println("crossed : " + parent1.hashCode() + " / " + parent2.hashCode() + " -> " + res.hashCode());
        return res;
    }
}
