package org.pitnashkiblya;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    static BufferedImage raw;
    static BufferedImage[][] tiles;
    static JFileChooser chooser = new JFileChooser();

    static void load() throws IOException {
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) raw = ImageIO.read(chooser.getSelectedFile());
    }
    static void preset() throws IOException {
        raw = ImageIO.read(new File("preset.jfif"));
    }
    static void cropToSquare(){
        int height = raw.getHeight();
        int width = raw.getWidth();
        int size = Math.min(height,width);
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        raw = raw.getSubimage(x, y, size, size);
    }
    static void cutToTiles(int num){
        int tileWidth = raw.getWidth() / num;
        int tileHeight = raw.getHeight() / num;
        tiles = new BufferedImage[num][num];
        for(int y = 0; y < num; y++)
            for (int x = 0; x < num; x++)
                tiles[y][x] = raw.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
    }
    static void resize(int size) {
        Image tmp = raw.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        raw = resized;
    }
    public static BufferedImage[][] process() {
        try {
            load();
            cropToSquare();
            resize(400);
            cutToTiles(4);
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
        return tiles;
    }
    public static BufferedImage[][] getBase() {
        try {
            preset();
            cropToSquare();
            resize(400);
            cutToTiles(4);
        }
        catch (Exception e) {
            System.out.println("Ошибка " + e);
        }
        return tiles;
    }
}
