package com.chottot.algogen.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class FactorySelector <T> extends JPanel implements ItemListener {

    private FactoryController<T> selectedController;

    public FactorySelector(List< FactoryController<T>> items) {
        super(new FlowLayout());
        JComboBox<FactoryController<T>> comboBox = new JComboBox<>();

        for ( FactoryController<T> item : items) {
            comboBox.addItem(item);
        }
        selectedController = items.get(0);

        comboBox.addItemListener(this);

        this.add(comboBox);
        this.add( selectedController);
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
