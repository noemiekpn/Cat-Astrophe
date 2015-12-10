package catladies.cat_astrophe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MazeActivity extends AppCompatActivity {
    // Player variables
    Player player;
    int startPos = 56,                  // Initial position in maze
        curPos   = startPos,            // Passive position of player
        finalPos = 15;                  // Goal position of maze

    // Maze variables
    private final int MAZE_SIZE = 8;    // Square maze dimension
    int squareSide;
    MazeView mazeView;                  // Custom view for game drawing

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
        // Screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        squareSide = size.x / MAZE_SIZE;

        // Types of squares to be drawn
        ShapeDrawable wall = new ShapeDrawable(new RectShape());
        wall.getPaint().setColor(Color.parseColor("#564b86"));
        wall.setIntrinsicHeight(squareSide);
        wall.setIntrinsicWidth(squareSide);

        Bitmap catBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nyan_cat_face);

        Bitmap bombBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
        BitmapDrawable bomb = new BitmapDrawable(getResources(), bombBitmap);

        Bitmap flagBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.finish_flag);
        BitmapDrawable flag = new BitmapDrawable(getResources(), flagBitmap);

        // Maze grid view to hold squares according to dimension
        GridLayout mazeGrid = (GridLayout) findViewById(R.id.maze_grid);
        mazeGrid.setColumnCount(MAZE_SIZE);
        mazeGrid.setRowCount(MAZE_SIZE);

        // Read maze map from text file
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(mazeFilePath)));

            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                // Split by whitespace
                String[] splitLine = line.split(" ");

                for(int i = 0; i < MAZE_SIZE; i++) {
                    ImageView cell = new ImageView(getApplicationContext());
                    cell.setBackgroundColor(Color.parseColor("#fffdd0"));

                    // Select type of square according to terrain
                    if(splitLine[i].equals("w")) {
                        cell.setImageDrawable(wall);
                    } else if(splitLine[i].equals("b")) {
                        cell.setImageDrawable(bomb);
                    } else if(splitLine[i].equals("s")) {
                        player = new Player(catBitmap, 0, 0);
                    } else if(splitLine[i].equals("f")) {
                        cell.setImageDrawable(flag);
                    }

                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.bottomMargin = 1;
                    param.rightMargin = 1;
                    param.height = squareSide;
                    param.width = squareSide;
                    param.setGravity(Gravity.CENTER);
                    param.columnSpec = GridLayout.spec(i);
                    param.rowSpec = GridLayout.spec(row);
                    cell.setLayoutParams(param);

                    mazeGrid.addView(cell);
                }

                row++;
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
    }

    //--------------------------------------------------------------------------------
    // Button handlers
    //--------------------------------------------------------------------------------
    public void moveUp(View view) {
        player.moveBy(0, -squareSide);
    }

    public void moveDown(View view) {
        player.moveBy(0, squareSide);
    }

    public void moveRight(View view) {
        player.moveBy(squareSide, 0);
    }

    public void moveLeft(View view) {
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
