package br.ufsm.csi.webAcess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.ufsm.csi.controller.LoginActivity;
import br.ufsm.csi.model.Usuario;

public class CadastrarUsuario extends AsyncTask<Integer, Double, String> {

    private Activity context;
    private ProgressDialog dialog;
    private Usuario usuario;

    public CadastrarUsuario(Activity context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(this.context, "Aguarde", "Cadastrando Usuário!", true, true);
    }

    @Override
    protected String doInBackground(Integer... params) {
        String url = "http://200.132.36.170:8080/AppWebServiceSpring/cadastrarUsuario";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());

        this.usuario = restTemplate.postForObject(url, this.usuario, Usuario.class);

        if(this.usuario != null) {
            return "ok";
        } else {
            return "erro";
        }
    }

    @Override
    protected void onPostExecute(String status) {

        dialog.dismiss();

        if(status.equals("ok")){
            Toast.makeText(this.context,
                "Ok! Usuário cadastrado!",
            Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.context,
                "Opa! Login ou senha invalido(s).",
            Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent();
        intent = new Intent(this.context, LoginActivity.class);
        context.startActivity(intent);
    }
}
