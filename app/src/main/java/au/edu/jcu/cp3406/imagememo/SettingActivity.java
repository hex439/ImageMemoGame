package au.edu.jcu.cp3406.imagememo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements
    View.OnTouchListener,
    GestureDetector.OnGestureListener {

    MediaPlayer song;

    private SwitchCompat switchSound;
    private SwitchCompat switchVibrate;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH_SOUND = "switchSound";
    public static final String SWITCH_VIBRATE = "switchVibrate";

    private boolean soundOnOff;
    private boolean vibrateOnOff;

    private GestureDetector gestureDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        song = MediaPlayer.create(this, R.raw.song);

        switchSound = findViewById(R.id.switchSound);
        switchVibrate = findViewById(R.id.switchVibrate) ;
        Button buttonSave = findViewById(R.id.buttonSave);

        //sensor that detects long press
        buttonSave.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, this);

        loadData();
        updateViews();

        if (switchSound.isChecked()) {
            song.start();
        }
    }

    //save settings
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH_SOUND, switchSound.isChecked());
        editor.putBoolean(SWITCH_VIBRATE, switchVibrate.isChecked());
        editor.apply();
        if (switchSound.isChecked()) {
            if (!song.isPlaying()) {
                song.start();
            }
        } else if (!switchSound.isChecked()) {
            if (song.isPlaying()) {
                song.pause();
            }
        }
        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
    }

    //load settings
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        soundOnOff = sharedPreferences.getBoolean(SWITCH_SOUND, false);
        vibrateOnOff = sharedPreferences.getBoolean(SWITCH_VIBRATE, false);
        if (switchSound.isChecked()) {
            if (!song.isPlaying()) {
                song.start();
            }
        } else if (!switchSound.isChecked()) {
            if (song.isPlaying()) {
                song.pause();
            }
        }
    }

    public void updateViews() {
        switchSound.setChecked(soundOnOff);
        switchVibrate.setChecked(vibrateOnOff);
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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        saveData();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return false;
    }
}
