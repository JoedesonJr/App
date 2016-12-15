package br.ufsm.csi.controller;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.ufsm.csi.R;
import br.ufsm.csi.model.Usuario;

public class Mapa_ClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_cliente);

        Intent intent = getIntent();
        MapaFragment mapaFragment = new MapaFragment();

        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        if(usuario != null){
            mapaFragment.setUsuario(usuario);
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mapa, mapaFragment);
        transaction.commit();
    }
}
