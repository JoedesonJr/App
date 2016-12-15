package br.ufsm.csi.webAcess;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.ufsm.csi.controller.Alterar_UsuarioActivity;
import br.ufsm.csi.controller.Principal_ClienteActivity;
import br.ufsm.csi.controller.Principal_TecnicoActivity;
import br.ufsm.csi.model.Usuario;

public class BuscarUsuario extends AsyncTask<Integer, Double, String> {

    private Activity context;
    private ProgressDialog dialog;
    private Usuario usuario;

    public BuscarUsuario(Activity context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(this.context, "Aguarde", "Buscando Usuário!", true, true);
    }

    @Override
    protected String doInBackground(Integer... params) {
        String url = "http://200.132.36.170:8080/AppWebServiceSpring/buscarUsuario";

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
            Intent intent = new Intent(context, Alterar_UsuarioActivity.class);
            intent.putExtra("usuario", this.usuario);
            context.startActivity(intent);
        } else {
            Toast.makeText(this.context,
                "Opa, Usuário não encontraro",
            Toast.LENGTH_LONG).show();
        }
    }
}
