package com.example.ibrahim.servisinfo_ib150071;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ibrahim.servisinfo_ib150071.Helper.MyApiRequest;
import com.example.ibrahim.servisinfo_ib150071.Helper.MyRunnable;
import com.example.ibrahim.servisinfo_ib150071.data.KompanijePregledVM;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Person> persons;
    private RecyclerView rv;
    private KompanijePregledVM podaci;
    private BaseAdapter adapter;
    private ListView lvKompanije;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);

  /*      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
// moje->

        Spinner grad=findViewById(R.id.gradIzbor);
        grad.setClickable(true);
        String[] items = new String[]{"Mostar", "Sarajevo", "Zenica"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        grad.setAdapter(adapter2);

        //
/*        ListView listaKompanija = (ListView) findViewById(R.id.lista);

        List<String> values = new ArrayList<>();

        for(int i=0;i<50;i++){
        values.add("Kompanijaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }
        List<String> values2 = new ArrayList<>();

        for(int i=0;i<2;i++){
            values2.add("Grad");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);


        ArrayAdapter<String> adapterSub = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_2, values2);

        listaKompanija.setAdapter(adapter);*/


        //card view lista

        lvKompanije=(ListView) findViewById(R.id.rv);



       popuniPodatkeTask();



    }

    private void popuniPodatkeTask() {
        MyApiRequest.get(this,"/api/kompanije", new MyRunnable<KompanijePregledVM>() {
            @Override
            public void run(KompanijePregledVM x) {
                podaci = x; //postavljeno kao field radi    adapter.notifyDataSetChanged(); za brisanje posiljke iz ListView
                popuniPodatke();
            }
        });
    }


private void popuniPodatke() {


       /* podaci=new KompanijePregledVM();
        podaci.rows=new ArrayList<KompanijePregledVM.Row>();
    KompanijePregledVM.Row r=new KompanijePregledVM.Row();

    r.Naziv="HEHE";
    r.Adresa="222";

    podaci.rows.add(r);*/


       KompanijePregledVM k=podaci;

    adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return podaci.rows.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item, parent, false);
            }
            TextView txtFirstLine = view.findViewById(R.id.NazivKompanijeTxt);
            TextView txtSecondLine = view.findViewById(R.id.AdresaKompanijeTxt);

            KompanijePregledVM.Row x = podaci.rows.get(position);

            txtFirstLine.setText(x.Naziv);
            txtSecondLine.setText(x.Adresa);
            return view;
        }
    };
    lvKompanije.setAdapter(adapter);


}
/*
    private void initializeData () {
        persons = new ArrayList<>();
        persons.add(new Person("Kompanija 1", "Mostar, ulica XVI br 82 061/123/456"));
        persons.add(new Person("Kompanija 2", "Mostar, ulica XVI br 82 061/123/456"));
        persons.add(new Person("Kompanija 3", "Mostar, ulica XVI br 82 061/123/456"));
        persons.add(new Person("Kompanija 4", "Mostar, ulica XVI br 82 061/123/456"));
        persons.add(new Person("Kompanija 5", "Mostar, ulica XVI br 82 061/123/456"));
        persons.add(new Person("Kompanija 6", "Mostar, ulica XVI br 8  061/123/456"));
        persons.add(new Person("Kompanija 7", "Mostar, ulica XVI b2      061/123/456"));
        persons.add(new Person("Kompanija 8", "Mostar, ulica XVr 82                                       061/123/456"));
        persons.add(new Person("Kompanija 9", "Mostar, ulicaI br 82                                    061/123/456"));

    }

    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(podaci); // treba mijenjati ovdje RVAdapter !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! i to je to u ovoj klasi
        rv.setAdapter(adapter);
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pocetna) {
            // Handle the camera action
        } else if (id == R.id.nav_upiti) {
            startActivity(new Intent(MainActivity.this,UpitiActivity.class));
        } else if (id == R.id.nav_postavke) {
            startActivity(new Intent(MainActivity.this,PostavkeActivity.class));

        } else if (id == R.id.nav_odjava) {
          /*finish();*/
            startActivity(new Intent(MainActivity.this,PonudeActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
