package au.edu.jcu.cp3406.imagememo;

import androidx.annotation.NonNull;
import java.util.Locale;

//Counter class
public class Counter {

    private int hours, minutes, seconds;

    Counter() {
        hours = minutes = seconds = 0;
    }


    void tick() {
        if (seconds < 60) {
            seconds += 1;
        }
        if (seconds == 60) {
            minutes += 1;
            seconds = 0;
        }

        if (minutes == 60) {
            hours +=1 ;
            minutes = 0;
            seconds = 0;
        }
    }


    @NonNull
    @Override
    //display formatting
    public String toString() {
        Locale locale = Locale.getDefault();
        return String.format(locale, "%02d:%02d:%02d", hours, minutes, seconds);
    }
}
