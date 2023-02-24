package com.example.simpleapp.userHandler;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;

import java.util.List;

public class ProfileInfo extends AppCompatActivity {

    private TextView tvName, tvPass, tvAlias, tvAge, tvLoc, tvPosScore, tvNegScore;
    DBHandler DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tvName = findViewById(R.id.textName);
        tvPass = findViewById(R.id.textPassword);
        tvAlias = findViewById(R.id.textAlias);
        tvAge = findViewById(R.id.textAge);
        tvLoc = findViewById(R.id.textLocation);
        tvPosScore = findViewById(R.id.textPosScore);
        tvNegScore = findViewById(R.id.textNegScore);

        DB = new DBHandler(getBaseContext());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String userName = sharedPreferences.getString("nameInUse_pref", "");

        List<String> userData = DB.selectUser(userName);

        if(!userData.isEmpty()){
            tvName.setText("Username:  " + userData.get(0));
            tvPass.setText("Password:  " + userData.get(1));
            tvAlias.setText("Alias:  " + userData.get(2));
            tvAge.setText("Age:  " + userData.get(3));
            tvLoc.setText("Location:  " + userData.get(4));
        }

        List<String> userScore = DB.selectScore(userName);

        if (!userScore.isEmpty()){
            tvPosScore.setText("Points:  " + userScore.get(1));
            tvNegScore.setText("Mistakes:  " + userScore.get(2));
        }
    }
}
