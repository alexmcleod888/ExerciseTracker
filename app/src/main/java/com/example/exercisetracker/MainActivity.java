package com.example.exercisetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    private Button newWorkoutButton;
    private Button loadWorkoutButton;
    private Button exitButton;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create database


        newWorkoutButton = (Button) findViewById(R.id.newWorkoutBtn);
        loadWorkoutButton = (Button) findViewById(R.id.loadWorkoutBtn);
        exitButton = (Button) findViewById(R.id.exitBtn);

       

        newWorkoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ChooseWorkoutActivity.class);
                startActivity(intent);

            }
        });

        loadWorkoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SavedWorkoutsActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                System.exit(0);
            }
        });

    }
}