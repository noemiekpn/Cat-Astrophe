package catladies.cat_astrophe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelSelectionActivity extends AppCompatActivity {
    public final static String NUM_BOMBS = "catladies.cat_astrophe.NUM_BOMBS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
    }

    /**
     * Start button handler
     */
    public void startGame(View view) {
        // Get button text
        Button button = (Button) view;
        String numBombs = button.getText().toString();

        Intent intent = new Intent(this, MazeActivity.class);
        intent.putExtra(NUM_BOMBS, Integer.parseInt(numBombs));
        startActivity(intent);
    }
}
