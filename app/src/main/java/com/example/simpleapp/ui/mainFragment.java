package com.example.simpleapp.ui;

import static com.example.simpleapp.R.id.playButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.simpleapp.Game;
import com.example.simpleapp.R;


public class mainFragment extends Fragment{

    private View v;
    private Button botonPlay;
    private MediaPlayer MP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);

        botonPlay = v.findViewById(playButton);
        MP = MediaPlayer.create(getContext(), R.raw.buttonsound);

        Context context = getContext();
        CharSequence text = "You need to log in";
        int duration = Toast.LENGTH_SHORT;
        Toast toasty = Toast.makeText(context, text, duration);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = preferences.getString("nameInUse_pref", "N/A");

        botonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!name.equals("N/A")){
                    MP.start();
                    cambiarActividad();
                }else{
                    toasty.show();
                }
            }
        });
        return v;
    }

    private void cambiarActividad() {
        Intent intentodeCambio = new Intent(this.getContext(), Game.class);
        startActivity(intentodeCambio);
    }

}