package com.fjc.guedr2.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fjc.guedr2.R;
import com.fjc.guedr2.fragment.CityListFragment;
import com.fjc.guedr2.fragment.CityPagerFragment;

/**
 * Created by javier on 16/4/16.
 */
public class CityPagerActivity extends AppCompatActivity {

    //Atributo para saber que indice de ciudad están pasando
    public static final String EXTRA_CITY_INDEX = "com.fjc.guedr2.activity.CityPagerActivity.EXTRA_CITY_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pager);


        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Le decimos a nuestra actividad que queremos usar esa vista toolbar como nuestra ToolBar
        setSupportActionBar(toolbar);

        //Añadimos un botón de retroceso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Como construir un fragment de manera dinamica
        FragmentManager fm = getFragmentManager();

        //Comprobamos primero que no tenemos añadido el fragment a nuestra jerarquia
        //Recuerda que las actividades de Android se recrean en varias situaciones
        //Giro del dispositivo
        //Falta de memoria
        //Sale el Teclado
        //Cambia el tamaño de la interfaz por algún motivo (split view)

        if (fm.findFragmentById(R.id.fragment_city_pager)==null) {

            //Pasamos los parametros al fragment CityPagerFragment
            int initialCityIndex = getIntent().getIntExtra(EXTRA_CITY_INDEX,0);

            //Ir a un fragment sin pasar parametros
            /*fm.beginTransaction()
                    .add(R.id.fragment_city_pager, new CityPagerFragment())
                    .commit();*/

            //Ir a un fragment pasando parametros
            fm.beginTransaction()
                    .add(R.id.fragment_city_pager, CityPagerFragment.newInstance(initialCityIndex))
                    .commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superSelected = super.onOptionsItemSelected(item);

        //Compruebo si han pulsado el boton de back que tiene un id especial (home)
        if (item.getItemId()== android.R.id.home){
            finish();
            return true;
        }

        return superSelected;
    }
}
