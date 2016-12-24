package com.damavandi.androidVlcSample;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rasad.damavandi.myvlc.R;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Fragment fragment = new PlayerFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragmen, fragment).commit();
    }
}
