package com.chottot.algogen.core;

import javax.swing.*;
import java.awt.*;

public class AlgoGenMemberRender extends JPanel {

    private AlgoGenMember member;

    public AlgoGenMemberRender() {
        member = null;
    }

    public AlgoGenMemberRender(AlgoGenMember member) {
        this.member = member;
    }

    public void setMember(AlgoGenMember member) {
        this.member = member;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if(member!= null){
            member.render((Graphics2D) g, this.getWidth(), this.getHeight());
        }
    }
}
