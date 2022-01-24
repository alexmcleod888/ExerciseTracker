package com.example.exercisetracker;


import android.os.Handler;
import android.widget.TextView;

import java.util.Locale;


public class Stopwatch
{
    private TextView elapsedTimeView;
    private int seconds;
    private boolean running;

    Stopwatch(TextView newElapsedTime, int newSeconds, boolean newRunning)
    {
        elapsedTimeView = newElapsedTime;
        seconds = newSeconds;
        running = newRunning;
    }

    public void setSeconds(int newSeconds)
    {
        seconds = newSeconds;
    }

    public void setRunning(Boolean newRunning)
    {
        running = newRunning;
    }

    //GeeksforGeeks, resource: https://www.geeksforgeeks.org/how-to-create-a-stopwatch-app-using-android-studio/
    public void startStopwatch()
    {
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%02d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                elapsedTimeView.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }

}
