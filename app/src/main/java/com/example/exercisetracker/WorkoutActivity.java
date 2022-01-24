package com.example.exercisetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WorkoutActivity extends AppCompatActivity {

    private ArrayList<Exercise> exerciseList;
    private String[] allExerciseStrings;

    //time
    private String currentTime;
    private long timeId;

    private RecyclerView recyclerView;

    private FloatingActionButton addExerciseBtn;
    private FloatingActionButton minusExerciseBtn;
    private Button saveBtn;
    private Button startBtn;
    private TextView elapsedTimeView;

    private EditText nameEditText;
    private TextView currentTimeTextView;

    private int numExercises;

    DatabaseHelper workoutDb;

    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    private static final int VERTICAL_ITEM_SPACE = 20;

    private Stopwatch stopwatch;

    private Boolean alreadyClicked = false;

    String s1[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);


        //setting current date
        currentTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        currentTime = "Date: " + new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
        timeId = Long.parseLong(new SimpleDateFormat("ddMMyyyyHHmmss").format(new java.util.Date()));

        exerciseList = new ArrayList<Exercise>();

        Intent intent = getIntent();
        allExerciseStrings = intent.getStringArrayExtra("exerciseStrings");

        exerciseList.add(new Exercise(allExerciseStrings, 0, 0, 0.0, ""));

        buildRecyclerView();

        addExerciseBtn = (FloatingActionButton) findViewById(R.id.addExerciseBtn);
        minusExerciseBtn = (FloatingActionButton) findViewById(R.id.minusExerciseBtn);
        startBtn = (Button) findViewById(R.id.startBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        currentTimeTextView = (TextView) findViewById(R.id.currentTime);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        elapsedTimeView = (TextView) findViewById(R.id.elapsedTime);


        stopwatch = new Stopwatch(elapsedTimeView, 0, false);
        stopwatch.startStopwatch();


        currentTimeTextView.setText(currentTime);



        workoutDb = new DatabaseHelper(this);

        Toast toast = Toast.makeText(WorkoutActivity.this, "No More Exercises Can Be Created", Toast.LENGTH_SHORT);

        addExerciseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(exerciseList.size() < 5) {
                    toast.cancel();
                    insertItem();
                }
                else {
                    toast.cancel();
                    toast.show();
                }
            }
        });

        minusExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(exerciseList.isEmpty() == false) {
                    removeItem();
                }
            }

        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //running = true;
                if(alreadyClicked == false) {
                    startBtn.setText("pause");
                    alreadyClicked = true;
                    stopwatch.setRunning(true);
                }
                else
                {
                    Toast.makeText(WorkoutActivity.this, "workout paused", Toast.LENGTH_LONG).show();
                    startBtn.setText("cont");
                    alreadyClicked = false;
                    stopwatch.setRunning(false);
                }

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] exerciseStrings = new String[5];
                String name = new String(nameEditText.getText().toString());
                for(int i = 0; i < exerciseList.size(); i++)
                {
                    Exercise currentExercise = exerciseList.get(i);

                    //gets the selected exercise from spinner
                   /* MyAdapter.MyViewHolder holder = (MyAdapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                    String selectedExercise = new String(holder.getSelectedSpinnerItem());
                    System.out.println(selectedExercise);
*/

                    exerciseStrings[i] = currentExercise.getSelectedExercise() + ',' + currentExercise.getSets() + ',' + currentExercise.getReps() + ',' + currentExercise.getWeight();

                }

                for(int i = 4; i > exerciseList.size() - 1; i-- )
                {
                    exerciseStrings[i] = "empty";
                }

                boolean isInserted = workoutDb.insertWorkoutData(name, timeId, exerciseStrings[0],
                        exerciseStrings[1], exerciseStrings[2], exerciseStrings[3], exerciseStrings[4]);

                if(isInserted == true)
                {
                    System.out.println(name);
                    System.out.println(timeId);
                    System.out.println(exerciseStrings[0]);
                    System.out.println(exerciseStrings[1]);
                    System.out.println(exerciseStrings[2]);
                    System.out.println(exerciseStrings[3]);
                    System.out.println(exerciseStrings[4]);
                    Toast.makeText(WorkoutActivity.this, "workout saved", Toast.LENGTH_LONG).show();
                    finish();

                }
                else
                {
                    Toast.makeText(WorkoutActivity.this, "workout was not saved", Toast.LENGTH_SHORT).show();
                    //finishActivity(RESULT_OK);
                    finish();
                }
            }
        });


    }

    @Override
    public void onSaveInstanceState( Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        stopwatch.setSeconds(0);
        stopwatch.setRunning(false);
    }

    public void insertItem()
    {
        exerciseList.add(new Exercise(allExerciseStrings, 0, 0, 0.0, ""));
        myAdapter.notifyItemInserted(exerciseList.size());
    }

    public void removeItem()
    {
        exerciseList.remove(exerciseList.size() - 1);
        myAdapter.notifyItemRemoved(exerciseList.size());
    }




    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


        //numExercises = 0;
        myLayoutManager = new LinearLayoutManager(this);
        myAdapter = new MyAdapter(exerciseList, this);
        //set spacing between items in recycler view
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setAdapter(myAdapter);
    }


}
