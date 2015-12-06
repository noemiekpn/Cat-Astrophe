package catladies.cat_astrophe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
    }

    /**
     * Start button handler
     */
    public void startGame(View view) {
        Intent intent = new Intent(this, LevelSelectionActivity.class);
        startActivity(intent);
    }
}
