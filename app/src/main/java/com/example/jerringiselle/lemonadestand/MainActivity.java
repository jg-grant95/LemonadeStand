package com.example.jerringiselle.lemonadestand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView textView, score;
    Button submitBtn;
    EditText answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView) findViewById(R.id.questionText);
        score=(TextView)findViewById(R.id.scoreText);
        submitBtn=(Button)findViewById(R.id.submitBtn);
        answer=(EditText)findViewById(R.id.answerText);

    }

    /*
    @Override
    public void onClick(){
        if(1); //should be a condition
        Problem problem=new Problem()
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("problem");
    }
    */

    public void loginClick(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
