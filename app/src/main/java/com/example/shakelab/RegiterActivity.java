package com.example.shakelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.ims.RegistrationManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

public class RegiterActivity extends AppCompatActivity {

    private EditText textEmail;
    private EditText textPassword;
    private EditText textNickname;

    private CheckBox Policy;

    private TextView BackToSignIn;

    ProgressBar progressBar;
    private Button SignUp;
    private Button btnVerify;

    public static final String TAG = "TAG";

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_window);



        BackToSignIn = findViewById(R.id.RegSignIn);

        textPassword = findViewById(R.id.EditTextPassword);
        textEmail = findViewById(R.id.EditTextEmail);
        textNickname = findViewById(R.id.EditTextNickname);

        Policy = findViewById(R.id.checkBoxPolicy);
        SignUp = findViewById(R.id.BtnSignUp);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = textEmail.getText().toString().trim();
                String Password = textPassword.getText().toString().trim();
                String Nickname = textNickname.getText().toString().trim();


                if(TextUtils.isEmpty(Nickname)){
                    textEmail.setError("Nickname is Required");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if(TextUtils.isEmpty(Email)){
                    textEmail.setError("Email is Required");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if (Password.length() < 6) {
                    textPassword.setError("Too small Password");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if(!Policy.isActivated()){
                    Policy.setError("Policy is Required");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(RegiterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("nickname",Nickname);
                            user.put("email",Email);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                }
                            });
                                VerifyUser();


                            try {
                                if(fAuth.getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }

                            }catch (Exception e){

                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "User Does not Created " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        BackToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void VerifyUser() {

        //Toast.makeText(getApplicationContext(), "Verification Email Hes been Sent", Toast.LENGTH_SHORT).show();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FirebaseUser fuser = fAuth.getCurrentUser();

        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Verification Email Hes been Sent", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
            }
        });
    }
}