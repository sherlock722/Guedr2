package com.fjc.guedr2.fragment;



import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fjc.guedr2.R;
import com.fjc.guedr2.model.Cities;
import com.fjc.guedr2.model.City;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityPagerFragment extends Fragment {

    private Cities mCities;
    private ViewPager mViewPager;
    public static final String ARG_CITY_INDEX = "ARG_CITY_INDEX";

    private int mInitialCityIndex;

    public CityPagerFragment() {
        // Required empty public constructor

    }

    //Lo que recibo de la actividad del pager (CityPagerActivity) se lo tiene que pasar a su fragment CityPagerFragment
    //Este método sirve para crear instancias de CityPagerFragment pasandole argumentos.
    //Desde la actividad CityPagerActivity llamamos a este método pasandole la posicion a la que quiero ir dentro del fragment
    public static CityPagerFragment newInstance (int position){

        CityPagerFragment fragment = new CityPagerFragment();

        Bundle arguments = new Bundle();
        arguments.putInt(ARG_CITY_INDEX,position);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Tengo menu (llama a los métodos que crean los menus)
        //Si un fragment tiene meú decir que tiene menu siempre
        setHasOptionsMenu(true);

        //Compruebo si me llegan argumentos al fragment
        if (getArguments() != null){

            mInitialCityIndex = getArguments().getInt(ARG_CITY_INDEX); //getInt devuelve cero si no recupera valor
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_city_pager, container, false);

        //Recupero el modelo
        mCities = new Cities();

        //Accedemos al ViewPager de nuestra interfaz
        //ViewPager pager = (ViewPager) root.findViewById(R.id.view_pager);
        //Creamos el View Pager como atributo para utilizarlo en los menus
        mViewPager = (ViewPager) root.findViewById(R.id.view_pager);

        //Cuantos fragment tiene nuestra interfaz >> Necesitamos lo que se llama la clase Adaptador
        //Permite unir el modelo con la interfaz. Pasamos el módelo al adaptador y el Pager sabrá  apartir del Adaptador
        //lo que tiene que poner


        //Le decimos al ViewPager quien es su adaptador que le dará los fragment que debe dibujar
        mViewPager.setAdapter(new CityPagerAdapter(getFragmentManager()));

        //Me entero cuando el usuario cambia de pagina en el ViewPager

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                               @Override
                                               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                               }

                                               //Aqui es donde el usuario ha cambiado de página
                                               @Override
                                               public void onPageSelected(int position) {

                                                   //updateCityInfo(position); //Anteriormente se pasaba la position
                                                   updateCityInfo();


                                               }

                                               @Override
                                               public void onPageScrollStateChanged(int state) {

                                               }
                                           }
        );


        //Me muevo a la ciudad indicada en mInitialCityIndex
        mViewPager.setCurrentItem(mInitialCityIndex);

        //updateCityInfo(0);
        //Anteriormente se pasaba la position
        updateCityInfo();

        return root;
    }
    public void updateCityInfo (){

        int position = mViewPager.getCurrentItem();
        //Modificamos el título de la Toolbar.
        //Para ello necesitamos:

        //1) Acceder a la actividad que nos contiene
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            //2)Acceder dentro de la actividad a la ActionBar
            ActionBar actionBar = activity.getSupportActionBar();

            //3) Cambiar el texto a la ToolBar
            actionBar.setTitle(mCities.getCities().get (position).getnName());
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Inflamos el menu
        inflater.inflate(R.menu.citypager,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean superValue2 = super.onOptionsItemSelected(item);

        //Comprobamos que opción de menu se ha pulsado
        if (item.getItemId() == R.id.previus ){
            //Retrocedemos una página (utilizamos el ViewPager)

            mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
            //Se cambia el titulo
            updateCityInfo();

            return true;

        } else if (item.getItemId() == R.id.next){
            //Avanzamos una página
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            //Se cambia el titulo
            updateCityInfo();

            return true;
        }


        return superValue2;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //Por si se llama antes del OnCreate
        //El ViewPager me dice en que posición estoy
        if (mViewPager != null){

            //Sacamos el mene anterior y siguiente
            MenuItem menuPrev = menu.findItem(R.id.previus);
            MenuItem menuNext = menu.findItem(R.id.next);

            //Los activo o los desactivo segun en la posicion en la que esté
            boolean nextEnable = mViewPager.getCurrentItem() < mCities.getCities().size()-1;
            menuNext.setEnabled(nextEnable);

            boolean prevEnable = mViewPager.getCurrentItem() > 0;
            menuPrev.setEnabled(prevEnable);

        }
    }
}

//Creamos la calse CityPagerAdapter
class CityPagerAdapter extends FragmentPagerAdapter { //v.13 support //FragmentPager Adapter es una clase abstracta

    private Cities mCities;
    public CityPagerAdapter(FragmentManager fm) {

        super(fm);
        mCities=new Cities();

    }
    //Que fragment va en una determinada posición
    @Override
    public android.app.Fragment getItem(int position) {

        //Vamos a pasar parametros al fragment a partir de argumentos (bundle)
        //String cityName = mCities.getCities().get(position).getnName();
        City city = mCities.getCities().get (position);

        /*FragmentForecast fragment = new FragmentForecast();

        Bundle arguments = new Bundle();
        arguments.putString(FragmentForecast.ARG_CITY_NAME, cityName);
        fragment.setArguments(arguments);*/

        //Esto es otra formas de hacer lo de justo encima (pasamos el nombre de la ciudad)
        //FragmentForecast fragment = FragmentForecast.newInstance(cityName);

        //Ahora pasamos la ciudad completa
        FragmentForecast fragment = FragmentForecast.newInstance(city);


        /* Necesito un fragment que devuelva el FragmentForecast */
        //return new FragmentForecast();
        return fragment;
    }

    //Número de partes en el ViewPAger
    @Override
    public int getCount() {

        //return 10;
        return mCities.getCities().size();
    }

    //Metodo para pintar el titulo de cada ViewPager
    @Override
    public CharSequence getPageTitle(int position) {
         super.getPageTitle(position);
        return mCities.getCities().get(position).getnName();
    }
}