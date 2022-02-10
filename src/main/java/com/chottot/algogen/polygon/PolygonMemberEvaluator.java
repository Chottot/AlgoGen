package com.chottot.algogen.polygon;

import com.chottot.algogen.Main;
import com.chottot.algogen.core.AlgoGenMemberEvaluator;

import java.awt.image.BufferedImage;

public class PolygonMemberEvaluator implements AlgoGenMemberEvaluator<PolygonMember> {

    private final BufferedImage target;

    public PolygonMemberEvaluator(BufferedImage target) {
        this.target = target;
    }

    @Override
    public double evaluate(PolygonMember member) {
        return 1.0 - Main.getImageDiff(target, member .generateImage(target.getWidth(), target.getHeight()));
    }
}
