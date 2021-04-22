package com.example.shakelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Profile extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;

    private TextView mail;
    private Button password;
    static FirebaseAuth fAuth;
    private Button Exit;

    private ImageView mail_feedBack;
    private ImageView phone_feedBack;
    private Button themeBnt;
    private TextView TheamColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        inicialization();
        //btnChangeMail();
        btnChangePass();
        createNavBar();
        createFeedbackBnts();
        //setTheme();
        btnExit();

    }

    public static int getColorWrapper(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//if actual version is >= 6.0
            return context.getColor(id);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(id);
        }
    }

    private void setTheme() {
        //themeBnt = findViewById(R.id.themeBnt);
        //TheamColor = findViewById(R.id.TheamColor);

        TheamColor.setTextColor(getResources().getColor(R.color.MainColour));
        themeBnt.setBackgroundColor(getResources().getColor(R.color.MainColour));

        themeBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openColorPicker();
            }
        });

    }

    int mDefoltColour;

    /*private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefoltColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefoltColour = color;

                getResources().getColor(R.color.MainColour);
                themeBnt.setBackgroundColor(color);
            }
        });
        colorPicker.show();

    }*/

    private void createFeedbackBnts() {
        phone_feedBack = findViewById(R.id.phone_feedBack);
        mail_feedBack = findViewById(R.id.mail_feedBack);

        mail_feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "shakelab@ask.com" });
                    startActivity(emailIntent);
            }
        });

        phone_feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });


    }

    private static final int REQUEST_CALL = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall();
            }else{
                Toast.makeText(this,"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makeCall() {

        if(ContextCompat.checkSelfPermission(Profile.this,
                Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Profile.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            //Toast.makeText(phone.this, "Please login", Toast.LENGTH_SHORT).show();
        }else{
            String contact_number="0980707638";
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + contact_number));
            startActivity(callIntent);
        }
    }

    private void btnExit() {

        Exit = findViewById(R.id.Log_out);

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile.this, "Logged out", Toast.LENGTH_SHORT).show();
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void btnChangeMail() {

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }

        });
    }


    private void openDialog() {
         ChangeMailDialog changeMailDialog = new ChangeMailDialog();
        changeMailDialog.show(getSupportFragmentManager(), "Change mail dialog");
    }

    private void btnChangePass() {
        password = findViewById(R.id.change_password);
        fAuth = FirebaseAuth.getInstance();

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();

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
    }

    private void inicialization() {
        mail = findViewById(R.id.profile_mail);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();

            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();
            mail.setText(email);
        }

    }


    private void createNavBar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigarion_drawer_open,R.string.navigarion_drawer_close);

        toggle.syncState();
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        break;
                    case R.id.nav_profile:
                        Toast.makeText(Profile.this, "Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        break;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        break;
                    case R.id.nav_create:
                        startActivity(new Intent(getApplicationContext(), Create.class));

                        break;
                }

                return false;
            }
        });
    }
}