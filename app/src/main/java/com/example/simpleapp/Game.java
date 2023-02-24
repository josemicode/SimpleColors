package com.example.simpleapp;

import static com.example.simpleapp.R.id.playButton;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpleapp.userHandler.DBHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity implements View.OnClickListener{

    Button upButton, midButton, downButton, backButton;
    TextView tv;
    String color;
    DBHandler DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DB = new DBHandler(getBaseContext());

        upButton = findViewById(R.id.Bone);
        midButton = findViewById(R.id.Btwo);
        downButton = findViewById(R.id.Bthree);
        backButton = findViewById(R.id.Bback);

        tv = findViewById(R.id.textView4);
        setRandomColor();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarActividad();
            }
        });

        upButton.setOnClickListener(this);
        midButton.setOnClickListener(this);
        downButton.setOnClickListener(this);

    }

    private void cambiarActividad() {
        Intent intentodeCambio = new Intent(this, MainActivity.class);
        startActivity(intentodeCambio);
    }

    private String colorText(){

        List<String> colors = new ArrayList<String>();
        String[] colorArray = new String[]{"Red", "Yellow", "Blue"};
        colors.addAll(Arrays.asList(colorArray));

        Collections.shuffle(colors);
        String res = colors.get(0);

        return res;
    }

    @Override
    public void onClick(View v) {
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = null;

        boolean correcto = false;

        switch (v.getId()){
            case R.id.Bone:
                if (color.equals("Red")){
                    text = getString(R.string.bien);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                    setRandomColor();
                    correcto = true;
                }else{
                    text = getString(R.string.mal);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                    correcto = false;
                }
                break;
            case R.id.Btwo:
                if (color.equals("Yellow")){
                    text = getString(R.string.bien);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                    setRandomColor();
                    correcto = true;
                }else{
                    text = getString(R.string.mal);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                    correcto = false;
                }
                break;
            case R.id.Bthree:
                if (color.equals("Blue")){
                    text = getString(R.string.bien);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                    setRandomColor();
                    correcto = true;
                }else{
                    text = getString(R.string.mal);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                    correcto = false;
                }
                break;
        }

        setScore(correcto);
    }

    public void setRandomColor() {
        String str = getString(R.string.elige);
        color = colorText();
        tv.setText(str + color);
    }

    public void setScore(boolean correct) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String userName = preferences.getString("nameInUse_pref", "");

        List<String> valores = DB.selectScore(userName);
        int aciertos = Integer.parseInt(valores.get(1));
        int errores = Integer.parseInt(valores.get(2));

        if(correct) {
            aciertos++;
        }else {
            errores++;
        }
        System.out.println(aciertos + " - " +errores);
        DB.updateScoreByName(userName, aciertos, errores);
    }

}