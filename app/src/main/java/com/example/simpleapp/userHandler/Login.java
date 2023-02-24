package com.example.simpleapp.userHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleapp.R;

import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener{

    EditText edName, edPass;
    Button buttonEnter;
    DBHandler DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edName = findViewById(R.id.editName2);
        edPass = findViewById(R.id.editPassword2);

        buttonEnter = findViewById(R.id.continueButton);
        buttonEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continueButton:
                DB = new DBHandler(Login.this);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = preferences.edit();

                String name = String.valueOf(edName.getText());
                String password = String.valueOf(edPass.getText());

                Context context = getApplicationContext();
                CharSequence text = "";
                int duration = Toast.LENGTH_SHORT;
                Toast toasty;

                List<String> datos = DB.selectUser(name);

                if(!datos.isEmpty()){
                    if (password.equals(datos.get(1))) {
                        editor.putString("nameInUse_pref", name);
                        editor.putString("alias_pref", datos.get(2));
                        editor.putString("age_pref", datos.get(3));
                        editor.putString("location_pref", datos.get(4));
                        editor.apply();

                        text = "You have signed in as " + name;
                    }else{
                        text = "Wrong password";
                    }
                    toasty = Toast.makeText(context, text, duration);
                    toasty.show();
                }else{
                    text = name + " is not a valid user";
                    toasty = Toast.makeText(context, text, duration);
                    toasty.show();
                }

                break;
        }
    }
}
