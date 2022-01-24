package com.example.exercisetracker;

import java.util.ArrayList;

public class WorkoutView {
    private String name;
    private String time;
    private ArrayList<Exercise> exerciseList;

    public WorkoutView(String newName, String newTime, ArrayList<Exercise> newExerciseList)
    {
        name = newName;
        newTime = newTime;
        exerciseList = newExerciseList;
    }
}
