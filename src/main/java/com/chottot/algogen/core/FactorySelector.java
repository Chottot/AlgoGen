package com.chottot.algogen.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FactorySelector <T> extends JPanel implements ItemListener {

    private final JComboBox<FactoryController<T>> comboBox;
    private FactoryController<T> selectedController;
    private List<FactoryController<T>> controllers;

    public FactorySelector(List< FactoryController<T>> items) {
        super(new FlowLayout());
        Random rand = new Random();
        comboBox = new JComboBox<>();

        setControllers(items);

        comboBox.addItemListener(this);
        //this.setBackground( new Color( rand.nextInt(256*3)));
        this.add(comboBox);
        this.add( selectedController);
    }

    public FactorySelector() {
        this(new ArrayList<>());
    }

    public void setControllers(List<FactoryController<T>> list){
        comboBox.removeAll();
        controllers = list;

        for ( FactoryController<T> item : list) {
            comboBox.addItem(item);
        }

        if(selectedController != null){
            remove(selectedController);
            selectedController = null;
        }

        if(!list.isEmpty()){
            selectedController = list.get(0);
        }
    }

    public List<FactoryController<T>> getControllers() {
        return controllers;
    }

    public T getSelectedCreation() {
        if( selectedController == null) return  null;
        return selectedController.create();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println("test" + e.getStateChange());
        if (e.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("test");
            if(selectedController != null) {
                this.remove(selectedController);
            }

            selectedController = (FactoryController<T>) e.getItem();
            this.add(selectedController);
        }
    }
}
