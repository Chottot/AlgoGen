package com.chottot.algogen.polygon.controller;

import com.chottot.algogen.AlgoGenRender;
import com.chottot.algogen.core.*;
import com.chottot.algogen.polygon.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class PolygonAlgoGenController extends JPanel implements Runnable {

    private Random rand;
    private final AlgoGenRender render;
    private final AtomicBoolean running = new AtomicBoolean(false);


    private PolygonAlgoGen gen;

    private final FactorySelector<PolygonMutator> mutatorFactorySelector;
    private final FactorySelector<PolygonEvaluator> evaluatorFactorySelector;
    private final FactorySelector<PolygonCrossOver> crossOverFactorySelector;
    private final FactorySelector< PolygonFactory> factoryFactorySelector;

    private BufferedImage target;

    private final JButton toggleButton;
    private final JButton nextGenButton;
    private final JButton createGenButton;

    private final JLabel label;
    private Thread thread;

    public PolygonAlgoGenController() {
        super(new BorderLayout());
        rand = new Random();
        render = new AlgoGenRender();

        ArrayList< FactoryController<PolygonMutator>> mutatorsList = new ArrayList<>();
        mutatorsList.add(new PolygonMutatorController());
        mutatorsList.add(new PolygonMutatorController());
        mutatorFactorySelector = new FactorySelector<>(mutatorsList);

        ArrayList<FactoryController<PolygonEvaluator>> evaluatorList = new ArrayList<>();
        evaluatorList.add(new PolygonEvaluatorController());
        evaluatorList.add(new PolygonEvaluatorController());
        evaluatorFactorySelector = new FactorySelector<>(evaluatorList);

        ArrayList<FactoryController<PolygonCrossOver>> crossOverList = new ArrayList<>();
        crossOverList.add(new SimpleCrossOverController());
        crossOverList.add(new SimpleCrossOverController());
        crossOverFactorySelector = new FactorySelector<>(crossOverList);

        ArrayList<FactoryController<PolygonFactory>> factoryList = new ArrayList<>();
        factoryList.add(new DefaultFactoryController(rand));
        factoryList.add(new DefaultFactoryController(rand));
        factoryFactorySelector = new FactorySelector<>(factoryList);

        JPanel controllerPanel = new JPanel();
        controllerPanel.setLayout(new BoxLayout(controllerPanel, BoxLayout.Y_AXIS));

        label = new JLabel();
        controllerPanel.add(label);

        nextGenButton = new JButton("next gen");
        nextGenButton.setEnabled(false);
        nextGenButton.addActionListener(e -> {
            nextGen();
        });

        createGenButton = new JButton("new gen");
        createGenButton.setEnabled(false);
        createGenButton.addActionListener(e -> {
            createGen();
        });

        toggleButton = new JButton("Resume");
        toggleButton.setEnabled(false);
        toggleButton.addActionListener(e -> {
            if (running.get()) {
                stop();
            } else {
                start();
            }
        });

        JButton imageButton = new JButton("Load image");
        imageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & PNG Images", "jpg", "png");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                stop();
                try {
                    setTarget(ImageIO.read(chooser.getSelectedFile()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        controllerPanel.add(factoryFactorySelector);
        controllerPanel.add(crossOverFactorySelector);
        controllerPanel.add(evaluatorFactorySelector);
        controllerPanel.add(mutatorFactorySelector);

        controllerPanel.add(imageButton);
        controllerPanel.add(createGenButton);
        controllerPanel.add(nextGenButton);
        controllerPanel.add(toggleButton);


        this.add(render, BorderLayout.CENTER);
        this.add(controllerPanel, BorderLayout.WEST);
    }

    private void setTarget(BufferedImage image)
    {
        this.target = image;
        for(FactoryController<PolygonFactory> f : factoryFactorySelector.getControllers()){
            ((DefaultFactoryController)f).setTarget(image);
        }

        for( FactoryController<PolygonEvaluator> f : evaluatorFactorySelector.getControllers()){
            ((PolygonEvaluatorController)f).setTarget(image);
        }
        createGenButton.setEnabled(true);
    }

    private void createGen(){
        stop();
        gen = new PolygonAlgoGen(   factoryFactorySelector.getSelectedCreation(),
                                    crossOverFactorySelector.getSelectedCreation(),
                                    mutatorFactorySelector.getSelectedCreation(),
                                    evaluatorFactorySelector.getSelectedCreation(),
                                    100,0.3,0.5);
        render.setGen(gen);
        nextGen();
        toggleButton.setEnabled(true);
        nextGenButton.setEnabled(true);
    }

    private void stop() {
        if (running.get()) {
            running.set(false);
            toggleButton.setText("Resume");
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void start() {
        if (!running.get()) {
            running.set(true);
            toggleButton.setText("Pause");
            thread = new Thread(this);
            thread.start();
        }
    }

    private void nextGen() {
        gen.nextGeneration();
        render.updatePop();
    }

    @Override
    public void run() {
        while (running.get()) {
            nextGen();
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
