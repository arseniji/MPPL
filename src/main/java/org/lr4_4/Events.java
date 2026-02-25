package org.lr4_4;

import javax.swing.*;

public class Events extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Events");
        JPanel panel = new JPanel();
        JButton btn1 = new JButton("нжами мане!");
        JButton btn2 = new JButton("нежме мена!");
        Senser s1 = new Senser(btn2,btn1);
        Senser s2 = new Senser(btn1,btn2);
        btn1.addActionListener(s1);
        btn2.addActionListener(s2);
        btn2.setEnabled(false);
        panel.add(btn1);
        panel.add(btn2);
        frame.setSize(300, 200);
        frame.add(panel);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
