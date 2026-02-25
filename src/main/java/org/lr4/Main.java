package org.lr4;

import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        JPanel p = new JPanel();
        JButton b1 = new JButton("нафик америку");
        JButton b2 = new JButton("да внатуре");
        JButton b3 = new JButton("нафик её");
        JButton b4 = new JButton("нафик её");
        JButton b5 = new JButton("нафик её");
        JButton b6 = new JButton("нафик её");
        JButton b7 = new JButton("нафик её");
        JButton b8 = new JButton("нафик её");
        JButton b9 = new JButton("нафик её");
        JButton b10 = new JButton("нафик её");
        JLabel lab = new JLabel("miay");
        f.setSize(300, 200);
        f.setLocation(500, 200);
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        p.add(b5);
        p.add(b6);
        p.add(b7);
        p.add(b8);
        p.add(b9);
        p.add(b10);
        p.add(lab);
        f.add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
