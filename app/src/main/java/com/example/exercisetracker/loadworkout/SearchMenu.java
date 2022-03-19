package com.example.exercisetracker.loadworkout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.exercisetracker.R;

public class SearchMenu extends AppCompatDialogFragment {
    private EditText editTextDate;
    private Button showAllBtn;
    private ExampleDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_search, null);

        editTextDate = view.findViewById(R.id.dateSearch);

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

    public interface ExampleDialogListener
    {
        void search(String date);
    }
}


