package com.example.exercisetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ChooseWorkoutActivity extends AppCompatActivity {

    private Button pullBtn;
    private Button pushBtn;
    private Button legBtn;
    private Button otherBtn;

    private String[] pullExerciseStrings;
    private String[] pushExerciseStrings;
    private String[] legExerciseStrings;
    private String[] allExerciseStrings;
    //private Button other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_workout);

        pullBtn = (Button) findViewById(R.id.pullBtn);
        pushBtn = (Button) findViewById(R.id.pushBtn);
        legBtn = (Button) findViewById(R.id.legsBtn);
        otherBtn = (Button) findViewById(R.id.otherBtn);

        pullBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*Intent intent = new Intent(ChooseWorkoutActivity.this, WorkoutActivity.class);
                startActivity(intent);*/
                pullExerciseStrings = getResources().getStringArray(R.array.pull_exercises);

                Intent intent = new Intent(ChooseWorkoutActivity.this, WorkoutActivity.class);
                intent.putExtra("exerciseStrings", pullExerciseStrings);
                intent.putExtra("name", "PULL");
                startActivity(intent);
                finish();
            }
        });

        pushBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent intent = new Intent(ChooseWorkoutActivity.this, WorkoutActivity.class);
                //startActivity(intent);
                pushExerciseStrings = getResources().getStringArray(R.array.push_exercises);

                Intent intent = new Intent(ChooseWorkoutActivity.this, WorkoutActivity.class);
                intent.putExtra("exerciseStrings", pushExerciseStrings);
                intent.putExtra("name", "PUSH");
                startActivity(intent);
                finish();
            }
        });

        legBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent intent = new Intent(ChooseWorkoutActivity.this, WorkoutActivity.class);
                //startActivity(intent);
                legExerciseStrings = getResources().getStringArray(R.array.leg_exercises);

                Intent intent = new Intent(ChooseWorkoutActivity.this, WorkoutActivity.class);
                intent.putExtra("exerciseStrings", legExerciseStrings);
                intent.putExtra("name", "LEGS");
                startActivity(intent);
                finish();
            }
        });

        otherBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent intent = new Intent(ChooseWorkoutActivity.this, WorkoutActivity.class);
                //startActivity(intent);
                allExerciseStrings = getResources().getStringArray(R.array.all_exercises);

                Intent intent = new Intent(ChooseWorkoutActivity.this, WorkoutActivity.class);
                intent.putExtra("exerciseStrings", allExerciseStrings);
                intent.putExtra("name", "OTHER");
                startActivity(intent);
                finish();
            }
        });




    }
}