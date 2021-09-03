package com.gholemhub.shakelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gholemhub.shakelab.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private EditText textEmail;
    private EditText textPassword;

    private Button SignIn;

    private TextView SignUp;
    private TextView ForgotPassword;

    static FirebaseFirestore fStore;
    static FirebaseAuth fAuth;

    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fAuth = FirebaseAuth.getInstance();


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
        textEmail = findViewById(R.id.editTextTextEmailAddress);
        textPassword = findViewById(R.id.editTextTextPassword);

        SignIn = findViewById(R.id.SignIn);

        SignUp = findViewById(R.id.SignUp);
        ForgotPassword = findViewById(R.id.ForgotPassword);
        fStore = FirebaseFirestore.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegiterActivity.class));
            }
        });

        forgotPassword();

        signIn();

    }

    private void forgotPassword() {
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter link to change passwort?");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //EXTRACT EMAIL AND SEND RESET LINK

                        String mail = resetMail.getText().toString();
                        if(!mail.isEmpty()){

                            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error Reset Lint is Not Sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(MainActivity.this, "No email, try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //CLOSE DIALOG
                    }
                });

                passwordResetDialog.create().show();
            }
        });
    }

    private void signIn() {
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = textEmail.getText().toString().trim();
                String Password = textPassword.getText().toString().trim();

                //ERRORS
                if (TextUtils.isEmpty(Email)) {
                    textEmail.setError("Email is Required");
                    return;
                }
                if (Password.length() < 6) {
                    textPassword.setError("Too small Password");
                    return;
                }
                //LOGIN
                fAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Sign In Successfully", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            startActivity(new Intent(getApplicationContext(), IntroActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Sign In Faled " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();

        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

    }
}
