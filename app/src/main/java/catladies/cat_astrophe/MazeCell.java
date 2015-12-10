package catladies.cat_astrophe;

/**
 * Created by Noemie on 09-Dec-15.
 */
public class MazeCell {
    int x;
    int y;
    int size;
    boolean wall;
    boolean bomb;

    public MazeCell(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public int getSize() { return size; }

    public void setWall(boolean wall) { this.wall = wall; }

    public void setBomb(boolean bomb) { this.bomb = bomb; }

    public boolean isWall() { return wall; }

    public boolean hasBomb() { return bomb; }


}
