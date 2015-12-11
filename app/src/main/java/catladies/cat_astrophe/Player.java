package catladies.cat_astrophe;

import android.graphics.Bitmap;

/**
 * Created by Noemie on 10-Dec-15.
 */
public class Player {
    Bitmap playerImg;
    int pos;            // The index position in the maze
    int x, y;           // The top left position of the image
    int life;          // According to number of bombs

    public Player(Bitmap playerImg, int x, int y, int pos, int score) {
        this.playerImg = playerImg;
        this.x = x;
        this.y = y;
        this.pos = pos;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public int getPos() { return pos; }

    public void setPos(int pos) { this.pos = pos; }

    public int getLife() { return life; }

    public void setLife(int life) { this.life = life; }

    public Bitmap getPlayerImg() { return playerImg; }

    public void moveBy(int incrX, int incrY) {
        x = x + incrX;
        y = y + incrY;
    }
}
