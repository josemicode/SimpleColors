package com.example.simpleapp.ui;

import static com.example.simpleapp.R.id.container;
import static com.example.simpleapp.R.id.imagenColor;
import static com.example.simpleapp.R.id.nxtButton;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.simpleapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class guideFragment extends Fragment {

    private View v;
    private Button nextPage;
    private ImageView imagen;
    private Integer source;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_guide, container, false);

        nextPage = v.findViewById(nxtButton);
        imagen = v.findViewById(imagenColor);

        source = R.drawable.yellow;

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarImg();
            }
        });

        return v;
    }

    private void cambiarImg() {
        List<Integer> imageList = new ArrayList<>();

        imageList.add(R.drawable.blue);
        imageList.add(R.drawable.red);
        imageList.add(R.drawable.yellow);

        for(int i = 0; i < imageList.size(); i++) {
            if (i!=(imageList.size()-1)){
                if(Objects.equals(source, imageList.get(i))){
                    imagen.setImageResource(imageList.get(i+1));
                    source=imageList.get(i+1);
                    break;
                }
            }else{
                    imagen.setImageResource(imageList.get(0));
                    source = imageList.get(0);
            }
        }
    }
}