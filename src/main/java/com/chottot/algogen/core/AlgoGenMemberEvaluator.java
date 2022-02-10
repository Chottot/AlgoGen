package com.chottot.algogen.core;

public interface AlgoGenMemberEvaluator<M extends AlgoGenMember> {
    double evaluate(M member);
}
