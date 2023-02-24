package com.example.simpleapp.userHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;

import java.sql.SQLException;

public class Registry extends AppCompatActivity implements View.OnClickListener{

    EditText edName, edAlias, edPass, edLoc, edAge;
    Button buttonEnter;
    DBHandler DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edName = findViewById(R.id.editName);
        edAlias = findViewById(R.id.editAlias);
        edAge = findViewById(R.id.editAge);
        edLoc = findViewById(R.id.editAddress);
        edPass = findViewById(R.id.editPassword);

        buttonEnter = findViewById(R.id.submitButton);
        buttonEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitButton:
                DB = new DBHandler(Registry.this);

                boolean cond = DB.addUser(edName.getText().toString(),
                        edPass.getText().toString(),
                        edAlias.getText().toString(),
                        Integer.parseInt(edAge.getText().toString()),
                        edLoc.getText().toString());

                Context context = getApplicationContext();
                CharSequence text = "";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = null;

                if (!cond){
                    text = edName.getText().toString() + " is not available";
                }else{
                    text = "Thank you for signing up!";
                }
                toast = Toast.makeText(context, text, duration);
                toast.show();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("nameInUse_pref", edName.getText().toString());
                editor.putString("alias_pref", edAlias.getText().toString());
                editor.putString("age_pref", edAge.getText().toString());
                editor.putString("location_pref", edLoc.getText().toString());
                editor.apply();

                String s = preferences.getString("nameInUse_pref", "");
                System.out.println(s);

                System.out.println(DB.selectUser(edName.getText().toString()));
                DB.closeDB();
                break;
        }
    }
}
