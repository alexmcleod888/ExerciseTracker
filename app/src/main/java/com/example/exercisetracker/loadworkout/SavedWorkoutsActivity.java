package com.example.exercisetracker.loadworkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.exercisetracker.DatabaseHelper;
import com.example.exercisetracker.newworkout.Exercise;
import com.example.exercisetracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SavedWorkoutsActivity extends AppCompatActivity implements SearchMenu.ExampleDialogListener, DatePickerDialog.OnDateSetListener{

    /*private ArrayList<String> nameList;
    private ArrayList<String> timeList;*/
    private ArrayList<WorkoutView> entireWorkoutViewList;
    private ArrayList<WorkoutView> currentWorkoutViewList;
    private DatabaseHelper workoutDb;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    private static final int VERTICAL_ITEM_SPACE = 5;
    //The index of the first item of the current set of workouts
    private int firstItemIndex;

    private Button nextBtn;
    private Button prevBtn;
    private Button showAllBtn;
    private FloatingActionButton searchBtn;

    private SearchMenu searchMenuFragment;

    //if we are showing all workout or searching specific
    private Boolean showAll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);

        nextBtn = (Button) findViewById(R.id.nextButton);
        prevBtn = (Button) findViewById(R.id.prevButton);
        showAllBtn = (Button) findViewById(R.id.showAllBtn);
        searchBtn = (FloatingActionButton) findViewById(R.id.searchBtn);

        firstItemIndex = 0;

        //load workouts from database into workoutView objects
        entireWorkoutViewList = new ArrayList<WorkoutView>();
        workoutDb = new DatabaseHelper(this);
        //load workouts from database in workoutView objects

        //if(showAll == true) {
        currentWorkoutViewList = entireWorkoutViewList;
       // }

        loadExercises();
        buildRecyclerView();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to next page if there is more workouts
                if(firstItemIndex + 5 < currentWorkoutViewList.size()) {
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

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchMenu();
            }
        });

        //purpose: shows the entire list of workouts
        showAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showAll == false) {
                    //loadExercises();
                    //create recycler view with workoutView objects
                    //createRecyclerView();
                    firstItemIndex = 0;
                    currentWorkoutViewList = entireWorkoutViewList;
                    setNewWorkoutList();
                    showAll = true;
                }


            }
        });

    }

    //purpose: loads all the workout views from the database
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
                entireWorkoutViewList.add(new WorkoutView(workoutName, timeCreated, exerciseList));

            }

            Collections.reverse(entireWorkoutViewList);


        }
    }

    //purpose: creates the recycler view the first time the activity is called
    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        int lastItem;


        //numExercises = 0;
        myLayoutManager = new LinearLayoutManager(this);
        ArrayList<WorkoutView> temporaryList = new ArrayList<WorkoutView>();

        //check if there is more then 5 items
        if(currentWorkoutViewList.size() < firstItemIndex + 5)
        {
            lastItem = currentWorkoutViewList.size();
        }
        else
        {
            lastItem = firstItemIndex + 5;
        }

        //get the first five workout views from the first index
        for(int i = firstItemIndex; i < lastItem; i++)
        {
            temporaryList.add(currentWorkoutViewList.get(i));
        }
        myAdapter = new MyWorkoutViewAdapter(temporaryList, this);
        //set spacing between items in recycler view
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setAdapter(myAdapter);
    }

    //purpose: called when a new set of workout views are loaded into the recyclerview
    public void setNewWorkoutList()
    {
        ArrayList<WorkoutView> temporaryList = new ArrayList<WorkoutView>();
        int lastItem;

        //check if there is more then 5 items
        if(currentWorkoutViewList.size() < firstItemIndex + 5)
        {
            lastItem = currentWorkoutViewList.size();
        }
        else
        {
            lastItem = firstItemIndex + 5;
        }

        //get the first five workout views from the first index
        for(int i = firstItemIndex; i < lastItem; i++)
        {
            temporaryList.add(currentWorkoutViewList.get(i));
        }
        ((MyWorkoutViewAdapter) myAdapter).setWorkoutList(temporaryList);
        myAdapter.notifyDataSetChanged();
    }

    //pursuse: opens a pop up dialogue to search the workouts
    public void openSearchMenu()
    {
        searchMenuFragment = new SearchMenu();
        searchMenuFragment.show(getSupportFragmentManager(), "search menu");

    }

    //purpose:handles text input from search menu
    @Override
    public void search(String date)
    {
        String regex;

        regex = "^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(date);

        if(matcher.matches() == true) {
            searchWorkoutList(date);
            firstItemIndex = 0;
            setNewWorkoutList();
        }
        else
        {
            Toast.makeText(this, "incorrect input", Toast.LENGTH_LONG).show();
        }
    }

    //when a search is made and a new list is required
    public void searchWorkoutList(String date)
    {
        ArrayList<WorkoutView> newWorkoutViewList = new ArrayList<WorkoutView>();
        for(WorkoutView var : entireWorkoutViewList)
        {
            if(var.getTime().equals(date))
            {
                newWorkoutViewList.add(var);
            }
        }
        currentWorkoutViewList = newWorkoutViewList; //set new list with the searched items
        showAll = false; //set it to not being the first time loaded1
    }

    //purpose: called when the DatePickerFragment is used to select a date and sends date to the
    //search menu
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String currentSearchDate;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

        currentSearchDate = format1.format(c.getTime());

        searchMenuFragment.setCurrentDate(currentSearchDate);
    }

}