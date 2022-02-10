package com.chottot.algogen.polygon.controller;

import com.chottot.algogen.core.FactoryController;
import com.chottot.algogen.polygon.PolygonCrossOver;
import com.chottot.algogen.polygon.PolygonMemberCrossOver;

public class SimpleCrossOverController extends FactoryController<PolygonCrossOver> {

    @Override
    public PolygonCrossOver create() {
        return new PolygonMemberCrossOver();
    }

    @Override
    public String toString() {
        return "SimpleCrossOverController";
    }
}
