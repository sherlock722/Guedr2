package com.fjc.guedr2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fjc.guedr2.R;

/**
 * Created by javier on 11/4/16.
 */
public class SettingActivity extends AppCompatActivity {

    public static final String EXTRA_CURRENT_UNIT = "extra_current_ubnit";

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Accedo a las vistas
        mRadioGroup = (RadioGroup) findViewById(R.id.units_rg);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Le decimos a nuestra actividad que queremos usar esa vista toolbar como nuestra ToolBar
        setSupportActionBar(toolbar);

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

        //Se recupera la infornmación de los parametros que nos envia la primera pantalla
        boolean showCelsius = getIntent().getBooleanExtra(EXTRA_CURRENT_UNIT,true); //Se asigna un valor por defecto si no nos pasa nada la primera actividad

        //En funcion de los que nos envie marcamos uno u otro radio button
        if (showCelsius){
            RadioButton celsiusRadius = (RadioButton) findViewById(R.id.celsius_rb);
            celsiusRadius.setChecked(true);
        } else{
            RadioButton farenhaidRadius = (RadioButton) findViewById(R.id.farenheit_rb);
            farenhaidRadius.setChecked(true);
        }

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

        //Es necesario para que viaje el intent y pare la actividad
        finish();
    }
}
