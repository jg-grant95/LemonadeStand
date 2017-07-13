package com.example.jerringiselle.lemonadestand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView, score;
    Button submitBtn;
    EditText answer;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference("problem");
    ArrayList<Problem> problems=new ArrayList<Problem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView) findViewById(R.id.questionText);
        score=(TextView)findViewById(R.id.scoreText);
        submitBtn=(Button)findViewById(R.id.submitBtn);
        answer=(EditText)findViewById(R.id.answerText);

        final DatabaseReference problemRef=database.getReference("problem");
        problemRef.orderByChild("problemDifficulty").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Problem problem=dataSnapshot.getValue(Problem.class);
                problems.add(problem);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        textView.setText(problems.get(0).getQuestion());
    }



    public void onClick(View v){

    }


    public void loginClick(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
