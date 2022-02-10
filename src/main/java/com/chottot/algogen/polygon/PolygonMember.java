
package com.chottot.algogen.polygon;

import com.chottot.algogen.core.AlgoGenMember;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class PolygonMember extends AlgoGenMember {

    private final int polygonNumber;
    private final ArrayList<PolygonColor> list;
    private BufferedImage image;

    public PolygonMember(Random rand, ArrayList<PolygonColor> list, int polygonNumber) {
        super(rand);
        this.list = list;
        this.polygonNumber = polygonNumber;
        image = null;
    }

    public PolygonMember(Random rand, ArrayList<PolygonColor> list) {
        this(rand, list, list.size());
    }

    public PolygonMember(Random rand, AlgoGenMember parent1, AlgoGenMember parent2, ArrayList<PolygonColor> list) {
        super(rand, parent1, parent2);
        this.list = list;
        this.polygonNumber = list.size();
    }

    public BufferedImage generateImage(int width, int height){
        if(image != null) return image;

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        for (PolygonColor pc : list) {
            g.setColor(pc.getColor());
            g.fillPolygon(pc);
        }
        g.dispose();

        return image;
    }

    public int getPolygonNumber(){
        return this.polygonNumber;
    }

    public PolygonColor getPolygonColor(int index){
        return list.get(index);
    }

    public ArrayList<PolygonColor> getList() {
        return list;
    }


    @Override
    public void render(Graphics2D g, int width, int height) {
        g.drawImage( generateImage(width, height), 0, 0, width, height, null);

        g.setColor(Color.BLACK);
        g.drawString( ""+fitness, 10, 10);

        g.drawString( ""+pickRate, 10, 25);
    }
}
