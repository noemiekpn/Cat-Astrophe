package catladies.cat_astrophe;

/**
 * Created by Noemie on 09-Dec-15.
 * Temporarily hard coded for prototyping
 */
public class MazeGrid {
    private MazeCell[] mazeCells;

    public MazeGrid(int mazeColumns, int mazeRows) {

       mazeCells = new MazeCell[mazeColumns * mazeRows];

    }

    public MazeCell[] getMazeCells() { return mazeCells; }
}
