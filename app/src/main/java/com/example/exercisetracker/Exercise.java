package com.example.exercisetracker;

import java.util.ArrayList;
import java.util.List;

public class Exercise {
    private List<String> exercisesTypes;
    private int sets;
    private int reps;
    private double weight;
    private String selectedExercise;

    public Exercise(String[] newExerciseTypes, int newSets, int newReps, double newWeight, String newSelectedExercise)
    {
        exercisesTypes = new ArrayList<String>();
        for (int i = 0; i < newExerciseTypes.length; i++) {
            exercisesTypes.add(newExerciseTypes[i]);
        }
        sets = newSets;
        reps = newReps;
        weight = newWeight;
        selectedExercise = newSelectedExercise;
    }

    public List<String> getExercises() {
        return exercisesTypes;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public double getWeight() {
        return weight;
    }

    public String getSelectedExercise() { return selectedExercise; }

    public void setReps(int newReps)
    {
        reps = newReps;
    }

    public void setSets(int newSets)
    {
        sets = newSets;
    }

    public void setWeight(double newWeight)
    {
        weight = newWeight;
    }

    public void setSelectedExercise(String newExercise)
    {
        selectedExercise = newExercise;
    }


}
