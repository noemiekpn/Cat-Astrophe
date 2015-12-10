package catladies.cat_astrophe;

/**
 * Created by Noemie on 09-Dec-15.
 */
public class MazeCell {
    boolean wall;
    boolean bomb;

    public MazeCell(boolean wall, boolean bomb) {
        this.wall = wall;
        this.bomb = bomb;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }


}
