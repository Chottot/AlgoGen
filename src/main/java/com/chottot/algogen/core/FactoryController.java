package com.chottot.algogen.core;

import javax.swing.*;
import java.awt.*;

public abstract class FactoryController <T> extends JPanel implements Factory<T> {
    public FactoryController(LayoutManager layout) {
        super(layout);
    }
}
