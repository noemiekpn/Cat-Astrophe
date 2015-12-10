package catladies.cat_astrophe;

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
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MazeActivity extends AppCompatActivity {
    private final int MAZE_SIZE = 8;
    int startPos;
    int finalPos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        GridLayout mazeGrid = (GridLayout) findViewById(R.id.maze_grid);
        int width = mazeGrid.getLayoutParams().width / MAZE_SIZE;
        int height = mazeGrid.getLayoutParams().height / MAZE_SIZE;

        mazeGrid.setColumnCount(MAZE_SIZE);
        mazeGrid.setRowCount(MAZE_SIZE);

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

//                    if(splitLine[i].equals("0"))
//                        square.getPaint().setColor(Color.WHITE);
//                    else if(splitLine[i].equals("1"))
//                        square.getPaint().setColor(Color.BLACK);
//                    else if(splitLine[i].equals("2"))
//                        square.getPaint().setColor(Color.RED);

                    cell.setImageDrawable(bomb);

                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.height = param.WRAP_CONTENT;
                    param.width = param.WRAP_CONTENT;
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
}
