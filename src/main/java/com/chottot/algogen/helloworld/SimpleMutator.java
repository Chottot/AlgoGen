package com.chottot.algogen.helloworld;

import java.util.Random;

public class SimpleMutator implements Mutator{

    private Random rand;

    public SimpleMutator(Random rand) {
        this.rand = rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    @Override
    public void mutate(StringMember member) {
        String str = member.getStr();
        int i = rand.nextInt(str.length());
        StringBuilder builder = new StringBuilder();
        if(i > 0){
            builder.append(str, 0, i);
        }

        builder.append( (char) rand.nextInt(32, 127));

        if(i < str.length() - 1){
            builder.append(str.substring(i + 1));
        }

        member.setStr(builder.toString());
    }
}
