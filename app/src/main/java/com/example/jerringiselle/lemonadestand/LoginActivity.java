package com.example.jerringiselle.lemonadestand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button signup_button, login_button, cancel_button, confirm_signup_button;
    EditText email_text, password_text, confirm_password_text;
    final static String TAG = "log_messages";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup_button = findViewById(R.id.signup);
        login_button = findViewById(R.id.login);
        email_text = findViewById(R.id.email);
        password_text = findViewById(R.id.password);
        confirm_password_text = findViewById(R.id.confirm_password);
        confirm_signup_button = findViewById(R.id.confirm_signup);
        cancel_button = findViewById(R.id.cancel);
        mAuth = FirebaseAuth.getInstance();


        /**
         * Performs some validation on the email and the password fields, then allows the user to
         * confirm their password
         */
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = email_text.getText().toString();
                final String password = password_text.getText().toString();
                Log.i(TAG, email);
                if(!isValidEmail(email) || !isValidPassword(password)){
                    return;
                }
                confirm_signup_button.setVisibility(View.VISIBLE);
                cancel_button.setVisibility(View.VISIBLE);
                login_button.setVisibility(View.GONE);
                signup_button.setVisibility(View.GONE);
                confirm_password_text.setVisibility(View.VISIBLE);
                if(checkConfirmPassword()){
                    Toast.makeText(LoginActivity.this, "The passwords don't match", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        /**
         * Calls the Firebase API to log the user in
         */
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = email_text.getText().toString();
                final String password = password_text.getText().toString();
                loginUser(email, password);
            }
        });

        /**
         * Performs validation on the fields, then brings up a section for the user to confirm
         * their password and sign into the application
         */
        confirm_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_text.getText().toString();
                String password = password_text.getText().toString();
                if(!checkConfirmPassword()){
                    Toast.makeText(LoginActivity.this, "Please make sure that the passwords match", Toast.LENGTH_LONG).show();
                    return;
                }
                signupUser(email, password);
                login_button.setVisibility(View.VISIBLE);
                signup_button.setVisibility(View.VISIBLE);
                cancel_button.setVisibility(View.GONE);
                confirm_signup_button.setVisibility(View.GONE);
                confirm_password_text.setVisibility(View.GONE);
            }
        });

        /**
         * resets the page to the original state
         */
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
    }


    /**
     * Signs the user up for the application. Uses Firebase to store the credentials
     * @param email the email of the user
     * @param password the password of the user
     */
    public void signupUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(LoginActivity.this,
                                                        "Verification email sent to " + user.getEmail(),
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.e(TAG, "sendEmailVerification", task.getException());
                                                Toast.makeText(LoginActivity.this,
                                                        "Failed to send verification email.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Checks if both passwords match
     * @return true if both passwords match, false if they don't
     */
    public boolean checkConfirmPassword() {
        return password_text.getText().toString().equals(confirm_password_text.getText().toString());
    }

    /**
     * Logs the user in using Firebase to check email and password
     * @param email the email of the user
     * @param password the password of the user
     */
    public void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if(!mAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(LoginActivity.this, "Email is not verified. Please verify email.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    /**
     * Checks if the user has entered in a valid username. Cannot be empty,
     * @param email the email of the user
     * @return true if a valid email, false if not
     */
    public boolean isValidEmail(String email){
        if(email.isEmpty()){
            Toast.makeText(this, "Please enter in a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Checks if the user has entered in a valid password, it must be at least 10 characters long
     * @param password the password of the user
     * @return true if it is a valid password, false if not
     */
    public boolean isValidPassword(String password){
        if(password.isEmpty()){
            Toast.makeText(this, "Please enter in a password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 10){
            Toast.makeText(this, "Please enter in a password that is at least 10 characters in length", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }




//    @Override
//    public void onStart(){
//        super.onStart();
//        //Check if user is signed in
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
}
