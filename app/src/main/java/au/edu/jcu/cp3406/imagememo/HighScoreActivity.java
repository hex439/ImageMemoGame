package au.edu.jcu.cp3406.imagememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    TextView scoreDisplay;
    int lastScore;

    public static final String SCORE = "score";

    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        scoreDisplay = findViewById(R.id.highScore);
        Button buttonShare = findViewById(R.id.buttonShare);

        //get score from game
        lastScore = getIntent().getIntExtra("lastScore", 0);

        saveData();

        //display high score
        updateViews();

        //click button to share to social media
        buttonShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String content = "I needed only " + highScore + " moves to solve this. What about you?";
            intent.putExtra(Intent.EXTRA_SUBJECT, content);
            startActivity(Intent.createChooser(intent, "Please select app"));
        });


    }

    //save high score
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SCORE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        highScore = sharedPreferences.getInt(SCORE, 0);

        //compare scores
        if (highScore == 0) {
            editor.putInt(SCORE, lastScore);
        }
        if (lastScore < highScore) {
            if (lastScore != 0) {
                editor.putInt(SCORE, lastScore);
            }
        }
        editor.apply();
    }

    public void updateViews() {
        SharedPreferences sharedPreferences = getSharedPreferences(SCORE, MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SCORE, 0);
        scoreDisplay.setText(String.valueOf(highScore));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}