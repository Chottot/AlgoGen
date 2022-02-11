package com.chottot.algogen.polygon;

import com.chottot.algogen.core.*;

import guru.nidi.graphviz.attribute.Image;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Size;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizCmdLineEngine;
import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.model.Factory;

import java.awt.image.BufferedImage;
import java.io.File;

public class PolygonAlgoGen extends SimpleAlgoGen<PolygonMember> {

    public PolygonAlgoGen(BufferedImage target, int polygonNumber, int pointByPolygon, int popNumber,
                          double mutationRate, int mutationStrength, double populationRateKeptThroughGeneration) {
        super(  new PolyGonMemberFactory(target.getWidth(), target.getHeight(), polygonNumber, pointByPolygon),
                new PolygonMemberCrossOver(),
                new PolygonMemberMutator(mutationStrength, target.getWidth(), target.getHeight()),
                new PolygonMemberEvaluator(target),
                popNumber, mutationRate, populationRateKeptThroughGeneration);
    }

    public PolygonAlgoGen(PolygonFactory memberInitializer,
                          PolygonCrossOver memberCrossOver,
                          PolygonMutator memberMutator,
                          PolygonEvaluator memberEvaluator,
                          int popNumber, double mutationRate, double populationRateKeptThroughGeneration) {
        super(memberInitializer, memberCrossOver, memberMutator, memberEvaluator, popNumber, mutationRate, populationRateKeptThroughGeneration);
    }

    public void setImage(BufferedImage img){
        reset();
        memberEvaluator = new PolygonMemberEvaluator(img);
    }

    @Override
    public BufferedImage getGraph() {
        MutableGraph g = Factory.mutGraph("test");

        Graphviz.useEngine(new GraphvizCmdLineEngine());

        //MutableNode n1 = Factory.mutNode(Label.html("<table border='0'><tr><td><img src='blue.png' /></td></tr></table>"));
        MutableNode n1 = Factory.mutNode("");
        MutableNode n2 = Factory.mutNode("test2");

        n1.add(Image.of("blue.png"));

       // n1.addLink( Factory.to(n2));
        g.add(n1);


        return Graphviz.fromGraph(g).basedir(new File("C:/Users/Cleme/IntelliJProjects/AlgoGen")).render(Format.PNG).toImage();
    }
}
