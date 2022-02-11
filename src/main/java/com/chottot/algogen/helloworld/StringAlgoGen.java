package com.chottot.algogen.helloworld;

import com.chottot.algogen.core.*;

import java.awt.image.BufferedImage;

public class StringAlgoGen extends SimpleAlgoGen<StringMember> {

    public StringAlgoGen(AlgoGenMemberFactory<StringMember> memberInitializer,
                         AlgoGenMemberCrossOver<StringMember> memberCrossOver,
                         AlgoGenMemberMutator<StringMember> memberMutator,
                         AlgoGenMemberEvaluator<StringMember> memberEvaluator,
                         int popNumber, double mutationRate, double populationRateKeptThroughGeneration) {
        super(memberInitializer, memberCrossOver, memberMutator, memberEvaluator, popNumber, mutationRate, populationRateKeptThroughGeneration);
    }

    @Override
    public BufferedImage getGraph() {
        return null;
    }
}
