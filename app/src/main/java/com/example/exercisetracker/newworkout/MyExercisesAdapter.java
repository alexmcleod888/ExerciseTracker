package com.example.exercisetracker.newworkout;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercisetracker.R;

import java.util.ArrayList;

public class MyExercisesAdapter extends RecyclerView.Adapter<MyExercisesAdapter.MyViewHolder> {

    private ArrayList<Exercise> exercisesList;
    private Context context;
    private int count;

    public MyExercisesAdapter(ArrayList<Exercise> newExerciseList, Context ct) {
        exercisesList = newExerciseList;
        context = ct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_row, parent, false);
        MyViewHolder evh = new MyViewHolder(v);
        return evh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyExercisesAdapter.MyViewHolder holder, int position) {

        Exercise currentExercise = exercisesList.get(position);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, currentExercise.getExercises());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.exerciseSpinner.setAdapter(adapter);
        holder.numSets.setText(Integer.toString(currentExercise.getSets()));
        holder.numReps.setText(Integer.toString(currentExercise.getReps()));
        holder.weight.setText(Double.toString(currentExercise.getWeight()));

        //get the item at the position chosen
        holder.exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentExercise.setSelectedExercise(holder.exerciseSpinner.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.numSets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            //sets the value in the exercise object after text is changed
            @Override
            public void afterTextChanged(Editable editable) {
                if(!holder.numSets.getText().toString().equals(""))
                {
                    currentExercise.setSets(Integer.parseInt((holder.numSets.getText()).toString()));
                }
            }
        });

        holder.numReps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            //sets the value in the exercise object after text is changed
            @Override
            public void afterTextChanged(Editable editable) {
                if(!holder.numReps.getText().toString().equals(""))
                {
                    currentExercise.setReps(Integer.parseInt((holder.numReps.getText()).toString()));
                }
            }
        });

        holder.weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            //sets the value in the exercise object after text is changed
            @Override
            public void afterTextChanged(Editable editable) {
                if(!holder.weight.getText().toString().equals(""))
                {
                    currentExercise.setWeight(Double.parseDouble((holder.weight.getText()).toString()));
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        Spinner exerciseSpinner;
        EditText numSets;
        EditText numReps;
        EditText weight;
        Exercise exercise;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseSpinner = itemView.findViewById(R.id.exerciseSpinner);
            numSets = itemView.findViewById(R.id.numSets);
            numReps = itemView.findViewById(R.id.numReps);
            weight = itemView.findViewById(R.id.weight);

        }

       /* public String getSelectedSpinnerItem()
        {
            String selectedItem = exerciseSpinner.getSelectedItem().toString();
            return selectedItem;
        }*/

        public boolean isNotNull()
        {
            if((numReps.getText().toString()).equals(""))
            {
                return false;
            }
            else{
                return true;
            }

        }
    }
}
