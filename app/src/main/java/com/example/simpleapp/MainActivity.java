package com.example.simpleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.simpleapp.ui.guideFragment;
import com.example.simpleapp.ui.mainFragment;
import com.example.simpleapp.ui.preferenceFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Button botonPlay;
    private Toolbar toolbar;
    private DrawerLayout DwL;
    private ActionBarDrawerToggle toggle;
    NavigationView NV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Context context = getApplicationContext();
        CharSequence text = "Hello " + preferences.getString("alias_pref", "N/A") + "!";
        int duration = Toast.LENGTH_SHORT;
        Toast toasty = Toast.makeText(context, text, duration);
        toasty.show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DwL = findViewById(R.id.drawer_layout);
        NV = findViewById(R.id.nav_view);
        NV.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, DwL, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        DwL.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new mainFragment()).commit();
            NV.setCheckedItem(R.id.nav_main);
        }

    }


    @Override
    public void onBackPressed() {
        if(DwL.isDrawerOpen(GravityCompat.START)){
            DwL.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_main:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new mainFragment()).commit();
                break;
            case R.id.nav_learn:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new guideFragment()).commit();
                break;
            case R.id.nav_pref:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new preferenceFragment()).commit();
                break;
        }
        DwL.closeDrawer(GravityCompat.START);
        return true;
    }
}

