package com.example.shakelab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//LIST OF INTROACTIVITIES
    addSlide(AppIntroFragment.newInstance("Welcome to ShakeLab app",
            "App which can help you to create and search new NO ALCOHOLIC shakes",
            R.drawable.ic_launcher_shake_lab_white_intro,
            ContextCompat.getColor(getApplicationContext(),
                    R.color.MainColour)));

    addSlide(AppIntroFragment.newInstance("Search",
            "Do you have ingredients and time you have able to make shakes which open your vision about\nNON ALCOHOLIC shakes",
            R.drawable.ic_outline_search_24,
            ContextCompat.getColor(getApplicationContext(),
                    R.color.MainColour)));

    addSlide(AppIntroFragment.newInstance("Create",
            "Shakes it is like a science, if you feel that you are good enough in it, you can try to make a new one!",
            R.drawable.ic_outline_science_24,
            ContextCompat.getColor(getApplicationContext(),
                    R.color.MainColour)));
    setFadeAnimation();

    addSlide(AppIntroFragment.newInstance("Lets start!",
            "You know what to do.\nGood luck!",
            R.drawable.ic_round_tag_faces_24,
            ContextCompat.getColor(getApplicationContext(),
                    R.color.MainColour)));
    setFadeAnimation();

        sharedPreferences = getApplication().getSharedPreferences("MyPrefrences", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();

    //CHECKING IF WE ALREADY SAW INTRO
    if(sharedPreferences != null){
        boolean checkState = sharedPreferences.getBoolean("checkState", false);
        if(checkState == true){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }
    }
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
//LISTENER OF SKIP BTN
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        editor.putBoolean("checkState", false).commit();//commit->save shanges
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
//LISTENER OF DONE BTN
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        editor.putBoolean("checkState", true).commit();//commit->save shanges
        finish();
    }
}