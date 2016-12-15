package br.ufsm.csi.webAcess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.ufsm.csi.controller.Principal_ClienteActivity;
import br.ufsm.csi.controller.Principal_TecnicoActivity;
import br.ufsm.csi.model.Usuario;

public class AutenticarUsuario extends AsyncTask<Integer, Double, String> {

    private Activity context;
    private ProgressDialog dialog;
    private Usuario usuario;

    public AutenticarUsuario(Activity context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(this.context, "Aguarde", "Autenticando Usuário!", true, true);
    }

    @Override
    protected String doInBackground(Integer... params) {
        String url = "http://200.132.36.170:8080/AppWebServiceSpring/autenticarUsuario";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());

        this.usuario = restTemplate.postForObject(url, this.usuario, Usuario.class);

        if(this.usuario != null) {
            return "autenticado";
        } else {
            return "não autenticado";
        }
    }

    @Override
    protected void onPostExecute(String status) {

        dialog.dismiss();

        if(status.equals("autenticado")){
            Toast.makeText(this.context,
                "Ok! Usuário Autenticado!",
            Toast.LENGTH_LONG).show();

            Intent intent = new Intent();

            if(usuario.getTecnico().equals("sim")) {
                intent = new Intent(this.context, Principal_TecnicoActivity.class);
            } else {
                intent = new Intent(this.context, Principal_ClienteActivity.class);
            }
            intent.putExtra("usuario", usuario);
            context.startActivity(intent);

        } else {
            Toast.makeText(this.context,
                "Opa! Login ou senha invalido(s).",
            Toast.LENGTH_LONG).show();
        }
    }

}
