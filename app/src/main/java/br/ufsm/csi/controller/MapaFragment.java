package br.ufsm.csi.controller;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.ufsm.csi.dao.UsuarioDao;
import br.ufsm.csi.model.Usuario;
import br.ufsm.csi.webAcess.EnderecosUsuarios;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback{

    private GoogleMap mapa;
    private Usuario usuario;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapa = googleMap;

        LatLng posicaoDaEscola = pegaCoordenadaDoEndereco("Colégio Politécnico da UFSM - Avenida Roraima - Camobi, Santa Maria - RS");
        if (posicaoDaEscola != null) {
            CameraUpdate update =
                CameraUpdateFactory.newLatLngZoom(posicaoDaEscola, 13);
            mapa.moveCamera(update);
        }

        if(getUsuario() != null){
            populaMapaAluno(getUsuario());
        }else{
            populaMapaTodosAlunos();
        }
    }

    public void populaMapaAluno(Usuario usuario){
        LatLng coordenada = pegaCoordenadaDoEndereco(usuario.getEndereco());
        if (coordenada != null) {
            MarkerOptions marcador = new MarkerOptions();
            marcador.position(coordenada);
            marcador.title(usuario.getNome());
            marcador.snippet(String.valueOf(usuario.getTelefone()));
            mapa.addMarker(marcador);
        }
    }
    public void populaMapaTodosAlunos(){
        UsuarioDao usuarioDao = new UsuarioDao(getContext(), "internetC");
        MapaFragment mapaFragment = new MapaFragment();
        for (Usuario usuario : usuarioDao.getUsuario()) {
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


    public LatLng pegaCoordenadaDoEndereco(String endereco) {
        try {
            Geocoder geocoder = new Geocoder (getActivity());
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {
                LatLng posicao = new
                        LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
