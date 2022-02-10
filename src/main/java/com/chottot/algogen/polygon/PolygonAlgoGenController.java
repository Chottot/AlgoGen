package com.chottot.algogen.polygon;

import com.chottot.algogen.AlgoGenRender;
import com.chottot.algogen.core.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class PolygonAlgoGenController extends JPanel implements Runnable {

    private final AlgoGenRender render;
    private final PolygonAlgoGen gen;
    private final AtomicBoolean running = new AtomicBoolean(false);

    private final FactorySelector<AlgoGenMemberMutator<PolygonMember>> mutatorFactorySelector;
    private final FactorySelector<AlgoGenMemberEvaluator<PolygonMember>> evaluatorFactorySelector;
    //private final FactorySelector<AlgoGenMemberCrossOver<PolygonMember>> crossOverFactorySelector;
    //private final FactorySelector<AlgoGenMemberFactory<PolygonMember>> factoryFactorySelector;

    private JButton toggleButton;
    private JLabel label;
    private Thread thread;

    public PolygonAlgoGenController(PolygonAlgoGen gen) {
        super(new BorderLayout());

        this.gen = gen;

        render = new AlgoGenRender(gen);

        ArrayList<FactoryController<AlgoGenMemberMutator<PolygonMember>>> mutatorsList = new ArrayList<>();
        mutatorsList.add(new PolygonMutatorController());
        mutatorFactorySelector = new FactorySelector<>(mutatorsList);

        ArrayList<FactoryController<AlgoGenMemberEvaluator<PolygonMember>>> evaluatorList = new ArrayList<>();

        evaluatorFactorySelector = new FactorySelector<>(evaluatorList);

        JPanel controllerPanel = new JPanel();
        controllerPanel.setLayout(new BoxLayout(controllerPanel, BoxLayout.Y_AXIS));

        label = new JLabel();
        controllerPanel.add(label);

        JButton nextGen = new JButton("next gen");
        nextGen.addActionListener(e -> {
            nextGen();
        });

        toggleButton = new JButton("start");
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
                    gen.setImage(ImageIO.read(chooser.getSelectedFile()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        controllerPanel.add(mutatorFactorySelector);
        controllerPanel.add(nextGen);
        controllerPanel.add(toggleButton);
        controllerPanel.add(imageButton);

        this.add(render, BorderLayout.CENTER);
        this.add(controllerPanel, BorderLayout.WEST);
    }


    private void stop() {
        if (running.get()) {
            running.set(false);
            toggleButton.setText("Start");
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
            toggleButton.setText("Stop");
            thread = new Thread(this);
            thread.start();
        }
    }

    private void nextGen() {
        long start = System.currentTimeMillis();
        gen.nextGeneration();
        render.updatePop();
        label.setText("gen: " + gen.getGenID());
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("finished + render: " + timeElapsed);
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
