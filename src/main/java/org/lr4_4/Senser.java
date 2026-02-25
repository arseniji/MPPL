package org.lr4_4;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Senser implements ActionListener {
    JButton presentbutton;
    JButton nextbutton;
    public Senser(JButton next,JButton present){
        this.presentbutton = present;
        this.nextbutton = next;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        presentbutton.setEnabled(false);
        nextbutton.setEnabled(true);
    }
}
