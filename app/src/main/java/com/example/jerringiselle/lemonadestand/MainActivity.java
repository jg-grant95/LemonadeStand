package com.example.jerringiselle.lemonadestand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView questionText, score;
    Button submitBtn;
    EditText answer;

    ArrayList<Problem> problems=new ArrayList<Problem>();
    private static final String TAG="myMessage";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionText = (TextView) findViewById(R.id.questionText);
        score = (TextView) findViewById(R.id.scoreText);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        answer = (EditText) findViewById(R.id.answerText);

        getData();
        populateFields();
    }



    public void onClick(View v){

    }


    public void loginClick(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public String problemToString(){
        String x="";
        for(int i=0; i<problems.size(); i++){
            x+=(problems.get(i).toString()+"\n");
        }
        return x;
    }

    private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            Problem problem=new Problem();

        }
    }

    private void populateFields(){
        Random rand=new Random();
        int value;
        if(problems.size()>0){
            value=problems.size();
        }else{
            return;
        }
        int index=rand.nextInt(value);
        questionText.setText(problems.get(index).getQuestion());
    }

    private void getData(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();
        myRef.child("problem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Problem problem=dataSnapshot.getValue(Problem.class);
                    problems.add(problem);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}