package com.chottot.algogen;

import com.chottot.algogen.core.AlgoGenMemberRender;
import com.chottot.algogen.core.SimpleAlgoGen;

import javax.swing.*;
import java.awt.*;

public class AlgoGenRender extends JPanel {

    private final SimpleAlgoGen<?> gen;
    private final AlgoGenMemberRender bestMemberRender;

    public AlgoGenRender(SimpleAlgoGen<?> gen) {
        super(new BorderLayout());
        this.gen = gen;
        bestMemberRender = new AlgoGenMemberRender();
        this.add(bestMemberRender, BorderLayout.CENTER);
    }

    public void updatePop(){
        this.bestMemberRender.setMember( gen.getBestMember());
        this.repaint();
    }
}
