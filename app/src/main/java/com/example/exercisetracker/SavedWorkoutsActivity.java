package com.example.exercisetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedWorkoutsActivity extends AppCompatActivity {

    /*private ArrayList<String> nameList;
    private ArrayList<String> timeList;*/
    private ArrayList<WorkoutView> workoutViewList;
    private DatabaseHelper workoutDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);

        //load workouts from database into workoutView objects
        workoutViewList = new ArrayList<WorkoutView>();
        workoutDb = new DatabaseHelper(this);
        //load workouts from database in workoutView objects
        loadExercises();
        //create recycler view with workoutView objects
        //createRecyclerView();
    }

    public void loadExercises()
    {
        Cursor res = workoutDb.getData();
        if(res.getCount() == 0)
        {
            Toast.makeText(SavedWorkoutsActivity.this, "No Entrys Exist", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            String workoutName;
            String timeCreated;
            String exerciseString;
            ArrayList<Exercise> exerciseList;
            String[] allExerciseStrings = getResources().getStringArray(R.array.all_exercises);
            //go through each workout stored in database
            while(res.moveToNext())
            {
                //stores list of exercises for each workout
                exerciseList = new ArrayList<Exercise>();
                workoutName = res.getString(0);
                timeCreated = res.getString(1);
                System.out.println(workoutName);
                System.out.println(timeCreated);
                //parse each string containing exercise information
                for(int i = 2; i < 7; i++)
                {
                    //check if the exercise is empty if so skip
                    if(!res.getString(i).equals("empty")) {
                        exerciseString = res.getString(i);
                        List<String> exerciseElements = Arrays.asList(exerciseString.split(","));
                        exerciseList.add(new Exercise(allExerciseStrings,
                                Integer.parseInt(exerciseElements.get(1)),
                                Integer.parseInt(exerciseElements.get(2)),
                                Double.parseDouble(exerciseElements.get(3)),
                                exerciseElements.get(0)));
                        System.out.println(exerciseString);
                    }
                }
                //store workoutlist in list of workoutViews
                workoutViewList.add(new WorkoutView(workoutName, timeCreated, exerciseList));
                //hello im alex
            }
        }
    }
}