package com.example.jerringiselle.lemonadestand;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ProblemCreation extends AppCompatActivity {

    Spinner spinner;
    EditText question, answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        spinner=(Spinner)findViewById(R.id.pickDifficulty);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.difficulty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        question=(EditText)findViewById(R.id.question);
        answer=(EditText)findViewById(R.id.answer);
    }

    public void addProblemClick(View v){
        if(!isQuestion(question.getText().toString())) return;
        if(!isAnswer(answer.getText().toString())) return;
        if(!isDifficulity(spinner.getSelectedItem().toString())) return;
    }

    //Check to see if question is entered, prompt user if not
    private boolean isQuestion(String x){
        if(x.isEmpty()){
            Toast toast= Toast.makeText(getApplicationContext(), "Please enter in a question", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    //Check to see if answer is entered, prompt user if not
    private boolean isAnswer(String x){
        if(x.isEmpty()){
            Toast toast= Toast.makeText(getApplicationContext(), "Please enter in an answer", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    //Check to see if a difficulty is entered, prompt user if not
    private boolean isDifficulity(String x){
        if(x.isEmpty()){
            Toast toast= Toast.makeText(getApplicationContext(), "Please enter in a difficulty", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

}
