package com.chottot.algogen.core;

import java.awt.image.BufferedImage;
import java.util.List;

public interface AlgoGen <M extends AlgoGenMember> {

    void initialisePopulation();

    void nextGeneration();

    void evaluatePopulation();

    M selectMember(List<M> list);

    BufferedImage getGraph();
}
