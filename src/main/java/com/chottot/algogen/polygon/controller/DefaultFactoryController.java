package com.chottot.algogen.polygon.controller;

import com.chottot.algogen.core.FactoryController;
import com.chottot.algogen.polygon.PolyGonMemberFactory;
import com.chottot.algogen.polygon.PolygonFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DefaultFactoryController extends FactoryController<PolygonFactory> {

    private BufferedImage target;
    private Random rand;

    private final JSlider polyNumberSlider;
    private final JLabel polyNumberLabel;

    private final JSlider pointByPolySlider;
    private final JLabel pointByPolyLabel;

    public DefaultFactoryController(Random rand) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.rand = rand;

        JPanel pane = new JPanel(new GridLayout());
        polyNumberSlider = new JSlider(1, 1000);
        polyNumberLabel = new JLabel("number of polygon: " + polyNumberSlider.getValue());
        polyNumberSlider.addChangeListener(e -> polyNumberLabel.setText("number of polygon: " + polyNumberSlider.getValue() ));

        pane.add(polyNumberLabel);
        pane.add(polyNumberSlider);
        this.add(pane);

        pane = new JPanel( new GridLayout());
        pointByPolySlider = new JSlider(3, 4);
        pointByPolyLabel = new JLabel("point by polygon: "+ pointByPolySlider.getValue());
        pointByPolySlider.addChangeListener(e -> pointByPolyLabel.setText("number of polygon: " + pointByPolySlider.getValue() ));

        pane.add(pointByPolyLabel);
        pane.add(pointByPolySlider);
        this.add(pane);
    }

    public void setTarget(BufferedImage target) {
        this.target = target;
    }

    @Override
    public PolygonFactory create() {
        return new PolyGonMemberFactory(rand, target.getWidth(), target.getHeight(), polyNumberSlider.getValue(), pointByPolySlider.getValue());
    }

    @Override
    public String toString() {
        return "DefaultFactoryController";
    }
}
