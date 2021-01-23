package com.example.shakelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText textEmail;
    private EditText textPassword;

    private Button SignIn;
    private Button GoogleSignIn;

    private TextView SignUp;
    private TextView ForgotPassword;

    FirebaseAuth fAuth;
    private GoogleSignInClient mGoogleSignInClient;


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
        GoogleSignIn = findViewById(R.id.GoogleSignIn);

        SignUp = findViewById(R.id.SignUp);
        ForgotPassword = findViewById(R.id.ForgotPassword);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegiterActivity.class));
            }
        });

        GoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRequestGoogleSignIn();
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();

                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Ypur Email To Receved Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //EXTRACT EMAIL AND SEND RESET LINK

                        String mail = resetMail.getText().toString();
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



        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = textEmail.getText().toString().trim();
                String Password = textPassword.getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    textEmail.setError("Email is Required");
                    return;
                }

                if (Password.length() < 6) {
                    textPassword.setError("Too small Password");
                    return;
                }

                fAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Sign In Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Sign In Faled " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                //startActivity(new Intent(getApplicationContext(), RegiterActivity.class));
            }
        });


    }

    private void CreateRequestGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }
}
