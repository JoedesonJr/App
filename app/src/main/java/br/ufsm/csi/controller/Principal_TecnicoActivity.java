package br.ufsm.csi.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.ufsm.csi.R;
import br.ufsm.csi.dao.UsuarioDao;
import br.ufsm.csi.model.Usuario;

public class Principal_TecnicoActivity extends AppCompatActivity {

    private TextView boas_vindas;
    private ListView usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_tecnico);

        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");

        usuarios = (ListView) findViewById(R.id.usuarios);
        boas_vindas = (TextView) findViewById(R.id.pagina_principal);
        boas_vindas.setText("Olá Técnico" +usuario);

        getUsuarios();
    }

    public void getUsuarios() {
        UsuarioDao dao = new UsuarioDao(this, "App_InternetC");

        ArrayAdapter<Usuario> adapter
            = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, dao.getUsuarios());

        usuarios.setAdapter(adapter);
    }
}
