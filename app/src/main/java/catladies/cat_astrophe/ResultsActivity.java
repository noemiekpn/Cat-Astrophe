package catladies.cat_astrophe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        int lifeResult = intent.getIntExtra(MazeActivity.LIFE_RESULT, 0);
        int timeResult = intent.getIntExtra(MazeActivity.TIME_RESULT, 0);
        TextView resultsTextView = (TextView) findViewById(R.id.resultsTextView);

        resultsTextView.setText("You exploded " + lifeResult + " bombs in " + timeResult + " seconds.");
    }

    public void returnMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
