package org.lr4_3;

import javax.swing.*;
import java.awt.*;

public class Counter{
    int counter = 10;
    JButton button;

    public Counter(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100,100);
        frame.setLayout(new FlowLayout());
        button = new JButton(String.valueOf(counter));
        button.addActionListener(e -> click());

        frame.add(button);
        frame.setVisible(true);
    }
    private void click(){
        if (counter == 1) System.exit(0);
        button.setText(String.valueOf(--counter));
    }
}
