package com.chottot.algogen;

import com.chottot.algogen.core.SimpleAlgoGen;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlgoGenController extends JPanel implements  Runnable{

    private final AlgoGenRender render;
    private final SimpleAlgoGen<?> gen;
    private final AtomicBoolean runnning = new AtomicBoolean(false);

    private Label label;
    private Thread thread;

    int i = 0;

    public AlgoGenController(SimpleAlgoGen<?> gen) {
        super(new BorderLayout());

        this.gen = gen;
        render = new AlgoGenRender();

        JPanel controllerPanel = new JPanel();
        controllerPanel.setLayout(new BoxLayout( controllerPanel, BoxLayout.Y_AXIS));

        label = new Label();
        controllerPanel.add(label);

        Button nextGen = new Button("next gen");
        nextGen.addActionListener(e -> {
            nextGen();
        });


        Button toggle = new Button("start");
        toggle.addActionListener(e -> {
            if(runnning.get()){
                runnning.set(false);
                toggle.setLabel("Start");
                try {
                    thread.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }else{
                runnning.set(true);
                toggle.setLabel("Stop");
                thread = new Thread(this);
                thread.start();
            }
        });

        controllerPanel.add(nextGen);
        controllerPanel.add(toggle);

        this.add(render, BorderLayout.CENTER);
        this.add(controllerPanel, BorderLayout.WEST);

    }

    private void nextGen(){
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
        while(runnning.get()){
            nextGen();
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
