package com.example.exercisetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyWorkoutViewAdapter extends RecyclerView.Adapter<MyWorkoutViewAdapter.MyViewHolder>{
    private ArrayList<WorkoutView> workoutList;
    private Context context;
    private int count;

    public MyWorkoutViewAdapter(ArrayList<WorkoutView> newWorkoutList, Context ct) {
        workoutList = newWorkoutList;
        context = ct;
    }

    public void setWorkoutList(ArrayList<WorkoutView> newWorkoutList)
    {
        workoutList = newWorkoutList;
    }

    @NonNull
    @Override
    public MyWorkoutViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_row, parent, false);
        MyWorkoutViewAdapter.MyViewHolder evh = new MyWorkoutViewAdapter.MyViewHolder(v);
        return evh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyWorkoutViewAdapter.MyViewHolder holder, int position) {

        WorkoutView currentWorkout = workoutList.get(position);
        holder.name.setText(currentWorkout.getName());
        holder.date.setText(currentWorkout.getTime());
        holder.exerciseList = currentWorkout.getExerciseList();
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        TextView date;
        Button editBtn;
        ArrayList<Exercise> exerciseList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView2);
            date = itemView.findViewById(R.id.dateView2);
            editBtn = itemView.findViewById(R.id.editButton);

        }
    }
}
