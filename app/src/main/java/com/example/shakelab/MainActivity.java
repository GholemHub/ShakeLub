package com.example.shakelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;

    private Button SignIn;
    private Button GoogleSignIn;

    private TextView SignUp;
    private TextView ForgotPassword;

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    ConstraintLayout root;
    FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);

        SignIn = findViewById(R.id.SignIn);
        GoogleSignIn = findViewById(R.id.GoogleSignIn);

        SignUp = findViewById(R.id.SignUp);
        ForgotPassword = findViewById(R.id.ForgotPassword);


    }

    private void Aurorization() {

        if (TextUtils.isEmpty(Email.getText().toString())) {
            Email.setError("Email is Required.");
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            //progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (TextUtils.isEmpty(Password.getText().toString())) {
            Password.setError("Password is Required.");
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            //progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        auth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(MainActivity.this, HomePage.class));
                        //progressBar.setVisibility(View.INVISIBLE);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "The email or password is not correct", Toast.LENGTH_LONG).show();
                //progressBar.setVisibility(View.INVISIBLE);
                //Snackbar.make(root, "Eroor Sign In " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}