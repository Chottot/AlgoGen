package com.chottot.algogen.core;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

public abstract class SimpleAlgoGen<T extends AlgoGenMember> implements AlgoGen<T> {

    private final Random rand;
    private final ThreadPoolExecutor executor;

    private final HashMap<T, Integer> map = new HashMap<>();

    protected final AlgoGenMemberFactory<T> memberInitializer;
    protected final AlgoGenMemberCrossOver<T> memberCrossOver;
    protected final AlgoGenMemberMutator<T> memberMutator;
    protected AlgoGenMemberEvaluator<T> memberEvaluator;

    public ArrayList<T> members;
    private final int popNumber;

    private final double mutationRate;
    private final double populationRateKeptThroughGeneration;

    private final AtomicReference<Double> fitnessMin = new AtomicReference<>();
    private final AtomicReference<Double> fitnessMax = new AtomicReference<>();
    private final AtomicReference<Double> fitnessSum = new AtomicReference<>();

    private final AtomicReference<T> bestMember = new AtomicReference<>();

    private int genID;

    public SimpleAlgoGen(Random rand, ThreadPoolExecutor executor,
                         AlgoGenMemberFactory<T> memberInitializer, AlgoGenMemberCrossOver<T> memberCrossOver,
                         AlgoGenMemberMutator<T> memberMutator, AlgoGenMemberEvaluator<T> memberEvaluator,
                         ArrayList<T> members, int popNumber,
                         double mutationRate, double populationRateKeptThroughGeneration) {
        this.rand = rand;
        this.executor = executor;
        this.memberInitializer = memberInitializer;
        this.memberCrossOver = memberCrossOver;
        this.memberMutator = memberMutator;
        this.memberEvaluator = memberEvaluator;
        this.members = members;
        this.popNumber = popNumber;
        this.mutationRate = mutationRate;
        this.populationRateKeptThroughGeneration = populationRateKeptThroughGeneration;
        genID = 0;
    }

    public SimpleAlgoGen(AlgoGenMemberFactory<T> memberInitializer, AlgoGenMemberCrossOver<T> memberCrossOver,
                         AlgoGenMemberMutator<T> memberMutator, AlgoGenMemberEvaluator<T> memberEvaluator,
                         int popNumber, double mutationRate, double populationRateKeptThroughGeneration) {
        this(new Random(), (ThreadPoolExecutor) Executors.newFixedThreadPool(10),
                memberInitializer, memberCrossOver,
                memberMutator, memberEvaluator,
                new ArrayList<>(), popNumber, mutationRate, populationRateKeptThroughGeneration);
        initialisePopulation();
    }

    public void reset(){
        bestMember.set(null);
        fitnessMin.set(0.0);
        fitnessMax.set(0.0);
        fitnessSum.set(0.0);
        members.clear();
        map.clear();
        genID = 0;
    }

    @Override
    public void initialisePopulation() {
        for (int i = 0; i < popNumber; i++) {
            members.add(memberInitializer.initialize());
        }

        evaluatePopulation();
    }

    @Override
    public void nextGeneration() {
        if (Objects.equals(fitnessMin.get(), fitnessMax.get())) return;

        int oldPopNumber = (int) (popNumber * populationRateKeptThroughGeneration);
        int newPopNumber = popNumber - oldPopNumber;
        ArrayList<T> oldPopulation = new ArrayList<>(oldPopNumber);
        ArrayList<T> newPopulation = new ArrayList<>(newPopNumber);

        map.clear();


        while (oldPopulation.size() < oldPopNumber) {
            T member = selectMember(members);
            oldPopulation.add(member);
            members.remove(member);
        }

        while (newPopulation.size() < newPopNumber) {
            T p1 = selectMember(oldPopulation);
            T p2 = selectMember(oldPopulation);
            while (p2 == p1){
                p2 = selectMember(oldPopulation);
            }

            newPopulation.add(memberCrossOver.crossOver(p1, p2));
        }

        for (T member : newPopulation) {
            if (rand.nextDouble() < mutationRate) {
                memberMutator.mutate(member);
            }
        }

        members.clear();
        members.addAll(oldPopulation);
        members.addAll(newPopulation);

       /* for (Map.Entry<T, Integer> entry : map.entrySet()) {
            System.out.println("f= " + entry.getKey().pickRate + " n= " + entry.getValue());
        }
        System.out.println(map.size());*/
        genID +=1;
        evaluatePopulation();
    }

    public int getPopSize() {
        return members.size();
    }

    public T getMember(int i) {
        return members.get(i);
    }

    @Override
    public void evaluatePopulation() {
        double fitnessSpace;

        long start = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(members.size());

        fitnessMin.set(Double.MAX_VALUE);
        fitnessMax.set(0.0);
        fitnessSum.set(0.0);

        for (T member : members) {

            executor.submit(() -> {
                member.fitness = memberEvaluator.evaluate(member);

                if (member.fitness < fitnessMin.get()) {
                    fitnessMin.set(member.fitness);
                }

                if (member.fitness > fitnessMax.get()) {
                    fitnessMax.set(member.fitness);
                    bestMember.set(member);
                }

                fitnessSum.set(fitnessSum.get() + member.fitness);
                latch.countDown();
            });

        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fitnessSpace = fitnessMax.get() - fitnessMin.get();
        //System.out.println("min: " + fitnessMin + " Max: " + fitnessMax + " sum: " + fitnessSum);

        for (T member : members) {

            //System.out.print(member.fitness);
            member.pickRate = (member.fitness - fitnessMin.get()) / fitnessSpace;
            //System.out.println("  " + member.pickRate);

           // System.out.println("eval: "+ member+" fit: " + member.fitness + " rate: " + member.pickRate );
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("finished in " + timeElapsed +" ms");
    }

    public T getBestMember(){
        return bestMember.get();
    }

    public int getGenID() {
        return genID;
    }

    @Override
    public T selectMember(List<T> list) {
        double fitnessSum = 0;

        for (T m : list) {
            fitnessSum += m.pickRate;
        }

        double chance = rand.nextDouble() * fitnessSum;

        for (T member : list) {
            chance -= member.pickRate;

            if (chance <= 0) {
                map.merge(member, 1, Integer::sum);
                return member;
            }
        }

        T member = members.get(rand.nextInt(members.size()));
        map.merge(member, 1, Integer::sum);

        return member;
    }

    public ArrayList<T> getMembers() {
        return members;
    }

    public BufferedImage generateTree(){
        BufferedImage img = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);

        return img;
    }

}

