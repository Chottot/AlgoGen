package com.chottot.algogen.polygon;

import com.chottot.algogen.polygon.controller.PolygonAlgoGenController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {

        PolygonAlgoGenController controller = new PolygonAlgoGenController();

        JFrame frame = new JFrame("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(controller);
        frame.setSize(new Dimension(500, 500));
        frame.setVisible(true);

    }

    public static double getImageDiff(BufferedImage im1, BufferedImage im2){

        double fitness = 0;
        for (int y = 0; y < im1.getHeight(); y++) {
            for (int x = 0; x < im1.getWidth(); x++) {
                int colorMember = im1.getRGB(x, y);
                int targetColor = im2.getRGB(x, y);

                int memberAlpha = (colorMember & 0xff000000) >> 24;
                int memberRed = (colorMember & 0x00ff0000) >> 16;
                int memberGreen = (colorMember & 0x0000ff00) >> 8;
                int memberBlue = colorMember & 0x000000ff;

                int targetAlpha = (colorMember & 0xff000000) >> 24;
                int targetRed = (targetColor & 0x00ff0000) >> 16;
                int targetGreen = (targetColor & 0x0000ff00) >> 8;
                int targetBlue = targetColor & 0x000000ff;

                fitness += (Math.abs(targetRed - memberRed) + Math.abs(targetGreen - memberGreen) + Math.abs(targetBlue - memberBlue) + Math.abs(targetAlpha - memberAlpha))/4.0;
            }
        }
        double maxDiff = 255L * im1.getWidth() * im1.getHeight();

        return fitness / maxDiff;
    }

}
