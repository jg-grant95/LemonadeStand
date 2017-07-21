package com.example.jerringiselle.lemonadestand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView questionText, score, difficultyText;
    Button submitBtn;
    EditText answer;
    int index;
    int easyPoints=5, mediumPoints=10, hardPoints=20;

    ArrayList<Problem> problems=new ArrayList<Problem>();
    private static final String TAG="myMessage";

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference().child("problem");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionText = (TextView) findViewById(R.id.questionText);
        difficultyText = (TextView) findViewById(R.id.showDifficulty);
        score = (TextView) findViewById(R.id.scoreText);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        answer = (EditText) findViewById(R.id.answerText);

        getData();
        Log.i(TAG, "got the data");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerRight(answer.getText().toString())){
                    updateScore();
                    populateFields();
                    answer.setText("");
                }
            }
        });

    }



    public void onClick(View v){

    }


    public void loginClick(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void populateFields(){
        Random rand=new Random();
        int value;
        if(problems.size()>0){
            value=problems.size();
        }else{
            return;
        }
        index=rand.nextInt(value);
        questionText.setText(problems.get(index).getQuestion());
        difficultyText.setText(problems.get(index).getProblemDifficulty().toString());
    }

    private void getData(){

        Log.i(TAG, "In getData() method");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "in the onDataChange() method");
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Problem problem=ds.getValue(Problem.class);
                    problems.add(problem);
                    Log.i(TAG, problem.toString());
                }
                populateFields();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "there was an error with the database"+ databaseError.getMessage());
            }
        });
    }

    private boolean answerRight(String x){
        int userAnswer=Integer.parseInt(x);
        int correctAnswer=Integer.parseInt(problems.get(index).getAnswer());
        if(userAnswer==correctAnswer){
            return true;
        }
        return false;
    }

    private void updateScore(){
        int currentScore=Integer.parseInt(score.getText().toString());
        int pointsAdded;
        switch (problems.get(index).getProblemDifficulty()){
            case easy:
                pointsAdded=easyPoints;
                break;
            case medium:
                pointsAdded=mediumPoints;
                break;
            case hard:
                pointsAdded=hardPoints;
                break;
            default:
                pointsAdded=0;
        }
        score.setText(""+(currentScore+pointsAdded));
    }
}