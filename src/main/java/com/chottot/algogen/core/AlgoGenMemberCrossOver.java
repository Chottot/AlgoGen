package com.chottot.algogen.core;

public interface AlgoGenMemberCrossOver<T extends AlgoGenMember> {
    T crossOver(T parent1, T parent2);
}
