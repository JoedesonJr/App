package br.ufsm.csi.webAcess;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import br.ufsm.csi.controller.LoginActivity;
import br.ufsm.csi.model.Usuario;

public class ListarUsuarios extends AsyncTask<Integer, Double, String> {
    private Activity context;
    private ProgressDialog dialog;
    private ListView lista;
    private Usuario[] usuarios;

    public ListarUsuarios(Activity context, ListView lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(this.context, "Aguarde", "Buscando Usuários!", true, true);
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

        dialog.dismiss();

        if(status.equals("ok")) {

            ArrayAdapter<Usuario> adapter
                = new ArrayAdapter<Usuario>(this.context, android.R.layout.simple_list_item_1, usuarios);
            lista.setAdapter(adapter);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context,
                        "Clique na posicao: " +position+ " Usuario: "+ usuarios[position].getNome(),
                    Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this.context,
                "Opa! Tente novamente.",
            Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent = new Intent(this.context, LoginActivity.class);
            context.startActivity(intent);
        }



    }
}
