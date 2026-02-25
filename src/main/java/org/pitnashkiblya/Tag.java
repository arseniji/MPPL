package org.pitnashkiblya;

import java.awt.image.BufferedImage;

public class Tag {
    private BufferedImage content;
    private int x;
    private int y;
    private int correctX;
    private int correctY;
    public Tag(int x, int y,BufferedImage content){
        this.x = x;
        this.y = y;
        this.correctX = x;
        this.correctY = y;
        this.content = content;
    }
    public BufferedImage getContent() {
        return content;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setCord(int x,int y){
        this.x = x;
        this.y = y;
    }
    public int getCorrectX() {return correctX;}
    public int getCorrectY() {return correctY;}
}