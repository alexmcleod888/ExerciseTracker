package com.example.exercisetracker.loadworkout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.exercisetracker.R;

import java.text.DateFormat;
import java.util.Calendar;

public class SearchMenu extends AppCompatDialogFragment {
    private EditText editTextDate;
    private Button selectDateBtn;
    private ExampleDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_search, null);

        editTextDate = view.findViewById(R.id.dateSearch);
        selectDateBtn = view.findViewById(R.id.selectDateBtn);

        builder.setView(view);
        builder.setTitle("Search");
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String date = editTextDate.getText().toString();
                listener.search(date);
            }
        });

        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
            }
        });

        return builder.create();
    }

    //purpose: assigns the ExampleDialogue listener and checks that it has been initialised by the calling activity.
    //Is called when the fragment is first created.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() +
                    "Must implement ExampleDialogueListener");
        }
    }

    //purpose:used to set the date when the date picker dialog is used
    public void setCurrentDate(String currentDate)
    {
        editTextDate.setText(currentDate);
    }

    public interface ExampleDialogListener
    {
        void search(String date);
    }
}


