package catladies.cat_astrophe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MazeActivity extends AppCompatActivity {
    // Player variables
    Player player;
    int startPos = 56,          // Initial position in maze
        curPos   = startPos,    // Passive position of player
        finalPos = 15;          // Goal position of maze

    // Maze variables
    private int mazeSize;       // Square maze dimension
    int squareSide;             // Square cell size in pixels
    MazeCell[] mazeCells;       // The matrix of cells of the maze
    MazeView mazeView;          // Custom view for game drawing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        createMaze("maze_1.txt");

        FrameLayout screenFrame = (FrameLayout) findViewById(R.id.screen_frame);
        mazeView = new MazeView(getApplicationContext());
        screenFrame.addView(mazeView);
    }

    /**
     * Creates layout structure of the maze based on input file.
     * @param mazeFilePath The input text file with the maze config.
     *                     It uses p for path, w for wall and b for bomb.
     *                     It uses s for start and f for final position.
     */
    private void createMaze(String mazeFilePath) {

        // Read maze map from text file
        BufferedReader reader = null;
        ArrayList<String> rows = new ArrayList<>();

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(mazeFilePath)));

            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // After reading we know the square maze dimensions
        mazeSize = rows.size();
        mazeCells = new MazeCell[mazeSize * mazeSize];

        // Screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        squareSide = size.x / mazeSize;

        // Types of squares to be drawn
        // The cat
        Bitmap catBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nyan_cat_face);

        // The bombs
        Bitmap bombBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
        BitmapDrawable bomb = new BitmapDrawable(getResources(), bombBitmap);

        // The goal flag
        Bitmap flagBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.finish_flag);
        BitmapDrawable flag = new BitmapDrawable(getResources(), flagBitmap);

        // Walls
        ShapeDrawable wall = new ShapeDrawable(new RectShape());
        wall.getPaint().setColor(Color.parseColor("#564b86"));
        wall.setIntrinsicHeight(squareSide);
        wall.setIntrinsicWidth(squareSide);

        // Maze grid view to hold squares according to dimension
        GridLayout mazeGrid = (GridLayout) findViewById(R.id.maze_grid);
        mazeGrid.setColumnCount(mazeSize);
        mazeGrid.setRowCount(mazeSize);

        // Variables for maze cell positions
        int x = 0, y = 0;

        for(int i = 0; i < mazeSize; i++) {
            // Split by whitespace
            String[] columns = rows.get(i).split(" ");

            // For each row, set up the columns
            for(int j = 0; j < columns.length; j++) {
                int index = i * mazeSize + j;
                mazeCells[index] = new MazeCell(x, y);

                    ImageView cellView = new ImageView(getApplicationContext());
                    cellView.setBackgroundColor(Color.parseColor("#fffdd0"));

                    // Select type of square according to terrain
                if (columns[j].equals("p")) {
                    mazeCells[index].setWall(false);
                    mazeCells[index].setBomb(false);
                } else if (columns[j].equals("w")) {
                    mazeCells[index].setWall(true);
                    mazeCells[index].setBomb(false);
                    cellView.setImageDrawable(wall);
                } else if (columns[j].equals("b")) {
                    mazeCells[index].setWall(false);
                    mazeCells[index].setBomb(true);
                    cellView.setImageDrawable(bomb);
                } else if (columns[j].equals("s")) {
                    mazeCells[index].setWall(false);
                    mazeCells[index].setBomb(false);
                    //player = new Player(catBitmap, 0, 0);
                    player = new Player(catBitmap, x, y);
                } else if (columns[j].equals("f")) {
                    mazeCells[index].setWall(false);
                    mazeCells[index].setBomb(false);
                    cellView.setImageDrawable(flag);
                }

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.bottomMargin = 1;
                param.rightMargin = 1;
                param.height = squareSide;
                param.width = squareSide;
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(j);
                param.rowSpec = GridLayout.spec(i);
                cellView.setLayoutParams(param);

                mazeGrid.addView(cellView);

                // Update (x, y) position
                x = x + squareSide;

                if(j != 0 && j % (mazeSize - 1) == 0) {
                    x = 0;
                    y = y + squareSide;
                }
            }
        }


    }

    //--------------------------------------------------------------------------------
    // Button handlers
    //--------------------------------------------------------------------------------
    public void moveUp(View view) {
        // Check for boundaries
        if(player.getY() - squareSide < 0) return;

        player.moveBy(0, -squareSide);
    }

    public void moveDown(View view) {
        // Check for boundaries
        if(player.getY() + squareSide > (mazeSize - 1) * squareSide) return;

        player.moveBy(0, squareSide);
    }

    public void moveRight(View view) {
        // Check for boundaries
        if(player.getX() + squareSide > (mazeSize - 1) * squareSide) return;

        player.moveBy(squareSide, 0);
    }

    public void moveLeft(View view) {
        // Check for boundaries
        if(player.getX() - squareSide < 0) return;

        player.moveBy(-squareSide, 0);
    }

    //--------------------------------------------------------------------------------
    // Custom view class
    //--------------------------------------------------------------------------------
    private class MazeView extends View {

        public MazeView(Context context) { super(context); }

        @Override
        protected void onDraw(Canvas canvas) {
            int x = player.getX();
            int y = player.getY();

            Rect rect = new Rect();
            rect.set(x, y, x + squareSide, y + squareSide);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            canvas.drawBitmap(player.getPlayerImg(), null, rect, paint);
            postInvalidate();
        }
    }
}
