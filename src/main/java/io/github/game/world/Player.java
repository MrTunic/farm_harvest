package io.github.game.world;

public class Player {

    private int x;
    private int y;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}
