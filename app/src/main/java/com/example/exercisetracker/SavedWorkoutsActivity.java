package com.example.exercisetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedWorkoutsActivity extends AppCompatActivity {

    /*private ArrayList<String> nameList;
    private ArrayList<String> timeList;*/
    private ArrayList<WorkoutView> workoutViewList;
    private DatabaseHelper workoutDb;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    private static final int VERTICAL_ITEM_SPACE = 5;
    //The index of the first item of the current set of workouts
    private int firstItemIndex;

    private Button nextBtn;
    private Button prevBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);

        nextBtn = (Button) findViewById(R.id.nextButton);
        prevBtn = (Button) findViewById(R.id.prevButton);

        firstItemIndex = 0;

        //load workouts from database into workoutView objects
        workoutViewList = new ArrayList<WorkoutView>();
        workoutDb = new DatabaseHelper(this);
        //load workouts from database in workoutView objects
        loadExercises();
        //create recycler view with workoutView objects
        //createRecyclerView();
        createView();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to next page if there is more workouts
                if(firstItemIndex + 5 < workoutViewList.size()) {
                    //Intent intent = new Intent(SavedWorkoutsActivity.this, SavedWorkoutsActivity.class);
                    //intent.putExtra("firstItemIndex", firstItemIndex + 5);
                    firstItemIndex = firstItemIndex + 5;
                    setNewWorkoutList();
                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to previous workouts if there are previous
                if(firstItemIndex - 5 >= 0) {
                    //Intent intent = new Intent(SavedWorkoutsActivity.this, SavedWorkoutsActivity.class);
                    //intent.putExtra("firstItemIndex", firstItemIndex - 5);
                    firstItemIndex = firstItemIndex - 5;
                    setNewWorkoutList();
                }
            }
        });

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
            String day;
            String month;
            String year;

            ArrayList<Exercise> exerciseList;
            String[] allExerciseStrings = getResources().getStringArray(R.array.all_exercises);
            //go through each workout stored in database
            while(res.moveToNext())
            {
                //stores list of exercises for each workout
                exerciseList = new ArrayList<Exercise>();
                workoutName = res.getString(0);
                timeCreated = res.getString(1);
                day = timeCreated.charAt(0) + "" + timeCreated.charAt(1);
                month = timeCreated.charAt(2) + "" + timeCreated.charAt(3);
                year = timeCreated.charAt(4) + "" + timeCreated.charAt(5) + "" + timeCreated.charAt(6) + "" + timeCreated.charAt(7);
                timeCreated = day + "/" + month + "/" + year;


                //System.out.println(workoutName);
                //System.out.println(timeCreated);
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
                        //System.out.println(exerciseString);
                    }
                }
                //store workoutlist in list of workoutViews
                workoutViewList.add(new WorkoutView(workoutName, timeCreated, exerciseList));
                //hello im alex
                //test 2
            }


        }
    }

    public void createView()
    {
        buildRecyclerView();
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        int lastItem;


        //numExercises = 0;
        myLayoutManager = new LinearLayoutManager(this);
        ArrayList<WorkoutView> temporaryList = new ArrayList<WorkoutView>();

        //check if there is more then 5 items
        if(workoutViewList.size() < firstItemIndex + 5)
        {
            lastItem = workoutViewList.size();
        }
        else
        {
            lastItem = firstItemIndex + 5;
        }

        //get the first five workout views from the first index
        for(int i = firstItemIndex; i < lastItem; i++)
        {
            temporaryList.add(workoutViewList.get(i));
        }
        myAdapter = new MyWorkoutViewAdapter(temporaryList, this);
        //set spacing between items in recycler view
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setAdapter(myAdapter);
    }

    public void setNewWorkoutList()
    {
        ArrayList<WorkoutView> temporaryList = new ArrayList<WorkoutView>();
        int lastItem;

        //check if there is more then 5 items
        if(workoutViewList.size() < firstItemIndex + 5)
        {
            lastItem = workoutViewList.size();
        }
        else
        {
            lastItem = firstItemIndex + 5;
        }

        //get the first five workout views from the first index
        for(int i = firstItemIndex; i < lastItem; i++)
        {
            temporaryList.add(workoutViewList.get(i));
        }
        ((MyWorkoutViewAdapter) myAdapter).setWorkoutList(temporaryList);
        myAdapter.notifyDataSetChanged();
    }
}