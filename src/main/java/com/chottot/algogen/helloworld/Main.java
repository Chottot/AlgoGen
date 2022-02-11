package com.chottot.algogen.helloworld;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random rand = new Random();

        String target = "paul la merde rgvberwghbxdtbhndtfxhnredsh";

        CrossOver crossOver = new SimpleCrossOver(rand);
        Evaluator evaluator = new SimpleEvaluator(target);
        Factory factory = new SimpleFactory(rand, target.length());
        Mutator mutator = new SimpleMutator(rand);

        StringAlgoGen gen = new StringAlgoGen(factory, crossOver, mutator, evaluator, 100, 0.7, 0.8);
        char test = 'Ø';
        System.out.println("test: " + (int)test);
        for (int i = 0; i < 10000; i++) {
            gen.nextGeneration();
            /*for (StringMember m : gen.getMembers()) {
                System.out.println("member "+ m.hashCode()+" \"" + m.getStr() +"\" at " + m.fitness+ " rate " + m.pickRate);
            }*/

            StringMember best = gen.getBestMember();
            System.out.println("Gen " + gen.getGenID() + " best \"" + best.getStr() +"\" at " + best.fitness);
            if(best.getStr().equals(target)){
                break;
            }
        }

        StringMember best = gen.getBestMember();
        System.out.println("END Gen " + gen.getGenID() + " best \"" + best.getStr() +"\" at " + best.fitness);

    }
}
