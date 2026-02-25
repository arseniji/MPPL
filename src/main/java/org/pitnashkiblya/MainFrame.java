package org.pitnashkiblya;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame {

    private int size = 4;
    private JButton[][] buttons;
    private Grid grid;
    private JPanel panel;

    public JPanel init() throws IOException {
        grid = new Grid(size);
        buttons = new JButton[size][size];
        JPanel panel = new JPanel(new GridLayout(size,size));
        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                JButton button = new JButton();
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttons[y][x] = button;
                int finalX = x;
                int finalY = y;
                button.addActionListener(e -> {
                    if (grid.move(finalX, finalY)) {
                        try {
                            refresh();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                panel.add(button);
            }
        }
        this.panel = panel;
        refresh();
        return panel;
    }

    private void refresh() throws IOException {
        Tag[][] area = grid.getArea();
        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                if (area[y][x].getContent() != null) {
                    buttons[y][x].setIcon(
                            new ImageIcon(area[y][x].getContent())
                    );
                } else {
                    buttons[y][x].setIcon(null);
                }
            }
        }
        if (grid.isFinal()) doFinal();
    }
    private void doFinal() {
        try {
            for (JButton[] row : buttons)
                for (JButton b : row) b.setEnabled(false);
            JDialog winDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(panel), "Победа!", true);
            winDialog.setLayout(new BorderLayout());

            ImageIcon winIcon = new ImageIcon("fireworks.gif");
            JLabel winLabel = new JLabel(winIcon);
            winLabel.setHorizontalAlignment(SwingConstants.CENTER);

            winDialog.add(winLabel, BorderLayout.CENTER);
            winDialog.pack();
            winDialog.setLocationRelativeTo(panel);
            winDialog.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}