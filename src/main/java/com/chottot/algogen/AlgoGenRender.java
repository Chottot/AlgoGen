package com.chottot.algogen;

import com.chottot.algogen.core.AlgoGenMemberRender;
import com.chottot.algogen.core.SimpleAlgoGen;

import javax.swing.*;
import java.awt.*;

public class AlgoGenRender extends JPanel {

    private SimpleAlgoGen<?> gen;
    private final AlgoGenMemberRender bestMemberRender;

    public AlgoGenRender() {
        super(new BorderLayout());
        bestMemberRender = new AlgoGenMemberRender();
        this.add(bestMemberRender, BorderLayout.CENTER);
    }

    public void setGen(SimpleAlgoGen<?> gen) {
        this.gen = gen;
    }

    public void updatePop(){
        this.bestMemberRender.setMember( gen.getBestMember());
        this.repaint();
    }
}
