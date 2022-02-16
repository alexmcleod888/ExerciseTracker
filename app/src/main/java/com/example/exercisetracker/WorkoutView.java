package com.example.exercisetracker;

import java.util.ArrayList;

public class WorkoutView {
    private String name;
    private String time;
    private ArrayList<Exercise> exerciseList;

    public WorkoutView(String newName, String newTime, ArrayList<Exercise> newExerciseList)
    {
        name = newName;
        time = newTime;
        exerciseList = newExerciseList;
    }

    public void setName(String newName)
    {
        name = newName;
    }

    public void setTime(String newTime)
    {
        time = newTime;
    }

    public void setExerciseList(ArrayList<Exercise> newExerciseList)
    {
        exerciseList = newExerciseList;
    }

    public String getName()
    {
        return name;
    }

    public String getTime()
    {
        return time;
    }

    public ArrayList<Exercise> getExerciseList()
    {
        return exerciseList;
    }
    
}
