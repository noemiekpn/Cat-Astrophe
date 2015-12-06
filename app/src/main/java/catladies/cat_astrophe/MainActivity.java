package catladies.cat_astrophe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Start button handler
     */
    public void selectLevel(View view) {
        Intent intent = new Intent(this, LevelSelectionActivity.class);
        startActivity(intent);
    }
}
