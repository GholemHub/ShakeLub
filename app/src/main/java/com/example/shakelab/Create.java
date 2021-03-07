package com.example.shakelab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Create extends AppCompatActivity {


    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


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

                        break;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        break;
                    case R.id.nav_create:
                        Toast.makeText(Create.this, "Create", Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });


    }
}