package catladies.cat_astrophe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
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
import android.widget.GridLayout;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MazeActivity extends AppCompatActivity {
    private final int MAZE_SIZE = 8;
    int startPos = 56,
        curPos = startPos,
        finalPos = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x / MAZE_SIZE;
        int height = width;

        GridLayout mazeGrid = (GridLayout) findViewById(R.id.maze_grid);
        mazeGrid.setColumnCount(MAZE_SIZE);
        mazeGrid.setRowCount(MAZE_SIZE);

        ShapeDrawable path = new ShapeDrawable(new RectShape());
        path.getPaint().setColor(Color.WHITE);
        path.setIntrinsicHeight(height);
        path.setIntrinsicWidth(width);

        ShapeDrawable wall = new ShapeDrawable(new RectShape());
        wall.getPaint().setColor(Color.BLACK);
        wall.setIntrinsicHeight(height);
        wall.setIntrinsicWidth(width);

        ShapeDrawable bomb = new ShapeDrawable(new RectShape());
        bomb.getPaint().setColor(Color.RED);
        bomb.setIntrinsicHeight(height);
        bomb.setIntrinsicWidth(width);

        // Read maze map from file
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("maze_1.txt")));

            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                // Split by whitespace
                String[] splitLine = line.split(" ");

                for(int i = 0; i < MAZE_SIZE; i++) {
                    ImageView cell = new ImageView(getApplicationContext());

                    if(splitLine[i].equals("0"))
                        cell.setImageDrawable(path);
                    else if(splitLine[i].equals("1"))
                        cell.setImageDrawable(wall);
                    else if(splitLine[i].equals("2"))
                        cell.setImageDrawable(bomb);

                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.bottomMargin = 1;
                    param.rightMargin = 1;
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

    // Custom view class
    private class MazeView extends View {
        public MazeView(Context context) { super(context); }

        @Override
        protected void onDraw(Canvas canvas) {
            Rect rect = new Rect();

            rect.set(0, 0, 100, 100);

            Paint paint = new Paint();
            paint.setColor(Color.CYAN);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawRect(rect, paint);
        }
    }
}
