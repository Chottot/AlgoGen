package com.chottot.algogen.polygon;

import java.awt.image.BufferedImage;

public class PolygonMemberEvaluator implements PolygonEvaluator {

    private BufferedImage target;

    public PolygonMemberEvaluator(BufferedImage target) {
        this.target = target;
    }

    public void setTarget(BufferedImage target) {
        this.target = target;
    }

    @Override
    public double evaluate(PolygonMember member) {
        return 1.0 - Main.getImageDiff(target, member .generateImage(target.getWidth(), target.getHeight()));
    }
}
