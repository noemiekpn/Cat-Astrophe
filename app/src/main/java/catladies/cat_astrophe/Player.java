package catladies.cat_astrophe;

import android.graphics.Bitmap;

/**
 * Created by Noemie on 10-Dec-15.
 */
public class Player {
    Bitmap playerImg;

    int x, y;           // The top left position of the image

    public Player(Bitmap playerImg, int x, int y) {
        this.playerImg = playerImg;
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public Bitmap getPlayerImg() { return playerImg; }

    public void moveTo(int dx, int dy) {
        x = dx;
        y = dy;
    }

    public void moveBy(int incrX, int incrY) {
        x = x + incrX;
        y = y + incrY;
    }
}
