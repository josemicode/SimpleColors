package com.example.simpleapp.ui;

import static com.example.simpleapp.R.id.deleteButton;
import static com.example.simpleapp.R.id.prefButton;
import static com.example.simpleapp.R.id.profileButton;
import static com.example.simpleapp.R.id.signinButton;
import static com.example.simpleapp.R.id.signupButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simpleapp.userHandler.DBHandler;
import com.example.simpleapp.userHandler.Login;
import com.example.simpleapp.R;
import com.example.simpleapp.userHandler.ProfileInfo;
import com.example.simpleapp.userHandler.Registry;
import com.example.simpleapp.preferenceActivity;

import java.util.List;

public class preferenceFragment extends Fragment implements View.OnClickListener{
    private View v;
    private Button bPref, bRegistro, bInicioSesion, bDelete, bProfile;
    private DBHandler DB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_preferences, container, false);

        bPref = v.findViewById(prefButton);
        bRegistro = v.findViewById(signupButton);
        bInicioSesion = v.findViewById(signinButton);
        bDelete = v.findViewById(deleteButton);
        bProfile = v.findViewById(profileButton);

        /*
        bPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarActividad();
            }
        });
         */

        bPref.setOnClickListener(this);
        bRegistro.setOnClickListener(this);
        bInicioSesion.setOnClickListener(this);
        bDelete.setOnClickListener(this);
        bProfile.setOnClickListener(this);

        return v;
    }

    private void cambiarActividad(Class clase) {
        Intent intentodeCambio = new Intent(this.getContext(), clase);
        startActivity(intentodeCambio);
    }

    @Override
    public void onClick(View v) {
        Context context = getContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        switch (v.getId()){
            case prefButton:
                if (!preferences.getString("nameInUse_pref", "N/A").equals("N/A")){
                    cambiarActividad(preferenceActivity.class);
                }else{
                    Toast toast = Toast.makeText(context, "Log in first", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case profileButton:
                if (!preferences.getString("nameInUse_pref", "N/A").equals("N/A")){
                    cambiarActividad(ProfileInfo.class);
                }else{
                    Toast toast = Toast.makeText(context, "Log in first", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case signupButton:
                cambiarActividad(Registry.class);
                break;
            case signinButton:
                cambiarActividad(Login.class);
                break;
            case deleteButton:
                System.out.println("EEEEEEEEEEEEE");

                DB = new DBHandler(context);
                SharedPreferences.Editor editor = preferences.edit();

                String name = preferences.getString("nameInUse_pref", "");

                CharSequence text = "";
                int duration = Toast.LENGTH_SHORT;
                Toast toasty;

                List<String> datos = DB.selectUser(name);

                if(!datos.isEmpty()){
                    DB.deleteUserByName(name);

                    editor.putString("nameInUse_pref", "N/A");
                    editor.putString("alias_pref", "N/A");
                    editor.putString("age_pref", "0");
                    editor.putString("location_pref", "N/A");
                    editor.apply();

                    text = name + " is no longer a user";
                }else{
                    text = name + " is not a valid user";
                }

                toasty = Toast.makeText(context, text, duration);
                toasty.show();

                break;
        }
    }
}
