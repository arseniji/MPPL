package org.pitnashkiblya;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
    private int size;
    private Tag[][] area;
    private int emptyX;
    private int emptyY;
    private Random random;

    public Grid(int size) {
        this.size = size;
        this.random = new Random();
        area = new Tag[size][size];
        init();
        shuffle();
    }

    private void init() {
        BufferedImage[][] temp = ImageProcessor.getBase();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                area[y][x] = new Tag(x, y, temp[y][x]);
            }
        }
        emptyX = size - 1;
        emptyY = size - 1;
        area[emptyY][emptyX] = new Tag(emptyX, emptyY, null);
    }

    public Tag[][] getArea() {
        return area;
    }

    public boolean move(int x, int y) {
        if (isNeighbor(x, y)) {
            Tag temp = area[y][x];
            area[y][x] = area[emptyY][emptyX];
            area[emptyY][emptyX] = temp;
            area[y][x].setCord(x, y);
            area[emptyY][emptyX].setCord(emptyX, emptyY);
            emptyX = x;
            emptyY = y;
            return true;
        }
        return false;
    }

    private boolean isNeighbor(int x, int y) {
        return (Math.abs(x - emptyX) == 1 && y == emptyY) ||
                (Math.abs(y - emptyY) == 1 && x == emptyX);
    }

    private void shuffle() {
        int shuffleMoves = 8;

        for (int i = 0; i < shuffleMoves; i++) {
            List<Tag> neighbors = new ArrayList<>(4);
            int[] dx = {0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0};
            for (int k = 0; k < 4; k++) {
                int nx = emptyX + dx[k];
                int ny = emptyY + dy[k];
                if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                    neighbors.add(area[ny][nx]);
                }
            }
            if (!neighbors.isEmpty()) {
                Tag movable = neighbors.get(random.nextInt(neighbors.size()));
                move(movable.getX(), movable.getY());
            }
        }
    }
    public boolean isFinal(){
        for(int y = 0; y < size; y++){
            for(int x = 0; x< size;x++){
                Tag temp = area[y][x];
                if(temp.getCorrectX() != x || temp.getCorrectY() != y) return false;
            }
        }
        return true;
    }
}