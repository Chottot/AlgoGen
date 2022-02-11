package com.chottot.algogen.helloworld;

public class SimpleEvaluator implements Evaluator{

    private String target;

    public SimpleEvaluator(String target) {
        this.target = target;
    }

    @Override
    public double evaluate(StringMember member) {
        String str = member.getStr();
        int val = 0;
        for (int i = 0; i < str.length(); i++) {
            val += Math.abs( str.charAt(i) - target.charAt(i) );
        }

        return 1./(val + 1);
    }
}
