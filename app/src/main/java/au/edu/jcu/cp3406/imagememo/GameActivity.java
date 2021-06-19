package au.edu.jcu.cp3406.imagememo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    private int flipped = 0;
    private int lastClicked = -1;
    private boolean clickAble = false;
    private Integer pairs = 0;
    private Counter counter;
    private Handler handler;
    private boolean isRunning;
    private TextView display;
    String time = "00:00:00";
    int moves = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        display = findViewById(R.id.score);

        //initiate counter class
        isRunning = false;
        counter = new Counter();

        //create an array list of game images
        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.ambulance);
        images.add(R.drawable.atom);
        images.add(R.drawable.flask);
        images.add(R.drawable.hazard);
        images.add(R.drawable.pill);
        images.add(R.drawable.rocket);
        images.add(R.drawable.ambulance);
        images.add(R.drawable.atom);
        images.add(R.drawable.flask);
        images.add(R.drawable.hazard);
        images.add(R.drawable.pill);
        images.add(R.drawable.rocket);


        //create a list of buttons
        Button[] buttons = new Button[12];
        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);
        buttons[5] = findViewById(R.id.button6);
        buttons[6] = findViewById(R.id.button7);
        buttons[7] = findViewById(R.id.button8);
        buttons[8] = findViewById(R.id.button9);
        buttons[9] = findViewById(R.id.button10);
        buttons[10] = findViewById(R.id.button11);
        buttons[11] = findViewById(R.id.button12);

        //shuffle images in image array list
        Collections.shuffle(images);


        //loop through the buttons (cards)
        for (int i = 0; i < 12; i++) {
            buttons[i].setText("cardBack");
            buttons[i].setTextSize(0.0F);
            int finalI = i;

            //onclick listener
            buttons[i].setOnClickListener(v -> {
                moves++;
                if (!isRunning) {
                    enableCounter();
                }

                //when a card is revealed
                if (buttons[finalI].getText() == "cardBack" && !clickAble) {
                    buttons[finalI].setBackgroundResource(images.get(finalI));
                    buttons[finalI].setText(images.get(finalI));
                    if (flipped == 0) {
                        lastClicked = finalI;
                    }
                    flipped++;

                //conceal a shown card
                } else if (buttons[finalI].getText() != "cardBack") {
                    buttons[finalI].setBackgroundResource(R.drawable.water);
                    buttons[finalI].setText("cardBack");
                    flipped--;
                }

                //when 2 cards match
                if (flipped == 2) {
                    clickAble = true;
                    if (buttons[finalI].getText() == buttons[lastClicked].getText()) {
                        buttons[finalI].setClickable(false);
                        buttons[lastClicked].setClickable(false);
                        clickAble = false;
                        flipped = 0;
                        pairs++;

                        //end game condition
                        if (pairs == 6) {
                            disableCounter();
                            time = counter.toString();

                            //phone vibrates
                            Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vi.vibrate(VibrationEffect.createOneShot(500,
                                        VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                vi.vibrate(500);
                            }

                            Toast.makeText(this, "Congratulations! Your time was "
                                    + time + " with " + moves + " moves needed!",
                                    Toast.LENGTH_LONG).show();

                            //auto send to high score activity
                            Intent intent = new Intent(getApplicationContext(),
                                    HighScoreActivity.class);
                            intent.putExtra("lastScore", moves);
                            startActivity(intent);
                            finish();

                        }
                    }

                //when all pairs have been found
                } else if (flipped == 0) {
                    clickAble = false;
                }
            });
        }
    }

    //start counter
    private void enableCounter() {
        isRunning = true;
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    counter.tick();
                    display.setText(counter.toString());
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    //end counter
    private void disableCounter() {
        isRunning = false;
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