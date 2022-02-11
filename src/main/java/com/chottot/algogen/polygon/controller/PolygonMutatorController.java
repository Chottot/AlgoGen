package com.chottot.algogen.polygon.controller;

import com.chottot.algogen.core.FactoryController;
import com.chottot.algogen.polygon.PolygonMemberMutator;
import com.chottot.algogen.polygon.PolygonMutator;

import javax.swing.*;
import java.awt.*;

public class PolygonMutatorController extends FactoryController<PolygonMutator> {

    private final JSlider mutationStrengthSlider;
    private final JLabel label;

    private int width;
    private int height;

    public PolygonMutatorController() {
        super( new FlowLayout());
        label = new JLabel("50");
        mutationStrengthSlider = new JSlider(0, 100);

        this.add(label);
        this.add(mutationStrengthSlider);

        mutationStrengthSlider.addChangeListener(e -> label.setText( ""+mutationStrengthSlider.getValue()));
    }

    void setTargetSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public PolygonMutator create() {
        return new PolygonMemberMutator(mutationStrengthSlider.getValue(), width, height);
    }

    @Override
    public String toString() {
        return "PolygonMutatorController";
    }
}
