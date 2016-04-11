package com.fjc.guedr2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

/**
 * Created by javier on 11/4/16.
 */
public class SettingActivity extends AppCompatActivity {


    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Accedo a las vistas
        mRadioGroup = (RadioGroup) findViewById(R.id.units_rg);

        findViewById(R.id.accept_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptSettings();
            }
        });

        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSettings();
            }
        });

    }

    private void cancelSettings() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void acceptSettings() {

        //Tengo que devolver a la pantalla principal lo que ha selecionado el usuario
        Intent returnIntent = new Intent();

        //putExtra para pasar datos a un intent (clave = units // valor = mRadioGroup.getCheckedRadioButtonId())
        returnIntent.putExtra("units", mRadioGroup.getCheckedRadioButtonId());

        setResult(RESULT_OK, returnIntent);

        finish();
    }
}
