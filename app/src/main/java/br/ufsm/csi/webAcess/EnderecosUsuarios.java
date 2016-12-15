package br.ufsm.csi.webAcess;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.ufsm.csi.controller.LoginActivity;
import br.ufsm.csi.controller.MapaFragment;
import br.ufsm.csi.model.Usuario;

public class EnderecosUsuarios extends AsyncTask<Integer, Double, String> {
    private Usuario[] usuarios;
    GoogleMap mapa;

    public EnderecosUsuarios(GoogleMap mapa) {
        this.mapa = mapa;
    }

    @Override
    protected String doInBackground(Integer... params) {
        String url = "http://200.132.36.170:8080/AppWebServiceSpring/listaUsuarios";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());
        usuarios = restTemplate.getForObject(url, Usuario[].class);

        if(this.usuarios != null) {
            return "ok";
        } else {
            return "erro";
        }
    }

    @Override
    protected void onPostExecute(String status) {
        if(status.equals("ok")) {
            MapaFragment mapaFragment = new MapaFragment();
            for (Usuario usuario : usuarios) {
                LatLng coordenada = mapaFragment.pegaCoordenadaDoEndereco(usuario.getEndereco());
                if (coordenada != null) {
                    MarkerOptions marcador = new MarkerOptions();
                    marcador.position(coordenada);
                    marcador.title(usuario.getNome());
                    marcador.snippet(usuario.getTelefone());
                    mapa.addMarker(marcador);
                }
            }
        }
    }
}
