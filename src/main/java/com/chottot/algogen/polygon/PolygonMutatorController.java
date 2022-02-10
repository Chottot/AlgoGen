package com.chottot.algogen.polygon;

import com.chottot.algogen.core.AlgoGenMemberMutator;
import com.chottot.algogen.core.FactoryController;

import javax.swing.*;
import java.awt.*;

public class PolygonMutatorController extends FactoryController<AlgoGenMemberMutator<PolygonMember>> {

    private final JSlider mutationStrengthSlider;
    private final JLabel label;

    public PolygonMutatorController() {
        super( new FlowLayout());
        label = new JLabel("50");
        mutationStrengthSlider = new JSlider(0, 100);

        this.add(label);
        this.add(mutationStrengthSlider);

        mutationStrengthSlider.addChangeListener(e -> label.setText( ""+mutationStrengthSlider.getValue()));
    }

    @Override
    public AlgoGenMemberMutator<PolygonMember> create() {
        return new PolygonMemberMutator(mutationStrengthSlider.getValue());
    }

    @Override
    public String toString() {
        return "PolygonMutatorController";
    }
}
