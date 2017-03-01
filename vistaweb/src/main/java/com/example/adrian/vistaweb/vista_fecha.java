package com.example.adrian.vistaweb;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class vista_fecha extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_fecha);
        DialogFragment newFragment = new fechas();

        newFragment.show(getFragmentManager(), "datePicker");
    }
}
