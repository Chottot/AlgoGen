package com.chottot.algogen.polygon.controller;

import com.chottot.algogen.core.FactoryController;
import com.chottot.algogen.polygon.PolygonEvaluator;
import com.chottot.algogen.polygon.PolygonMemberEvaluator;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PolygonEvaluatorController extends FactoryController<PolygonEvaluator> {

    private BufferedImage target;

    public PolygonEvaluatorController() {
        super(new FlowLayout());
    }

    public void setTarget(BufferedImage target) {
        this.target = target;
    }

    @Override
    public PolygonEvaluator create() {
        return new PolygonMemberEvaluator(target);
    }

    @Override
    public String toString() {
        return "PolygonEvaluatorController";
    }
}
