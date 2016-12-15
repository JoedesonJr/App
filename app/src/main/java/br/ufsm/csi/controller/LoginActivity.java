package br.ufsm.csi.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.ufsm.csi.R;
import br.ufsm.csi.dao.UsuarioDao;
import br.ufsm.csi.model.Usuario;
import br.ufsm.csi.webAcess.AutenticarUsuario;

public class LoginActivity extends AppCompatActivity {

    private boolean status;

    private EditText ed_Login;
    private EditText ed_Senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_Login = (EditText) findViewById(R.id.ed_usuario_form);
        ed_Senha = (EditText) findViewById(R.id.ed_senha_form);
    }

     public void Login(View view) {
         try {
             /*  AUTENTICAR USUÁRIO VIA WEBSERVICE  */
             new AutenticarUsuario(this, getUsuario()).execute();
         } catch(Exception e) {
             Toast.makeText(this,
                "Opa! Verifique sua conexão com a Internet.",
             Toast.LENGTH_LONG).show();
         }

         /*  AUTENTICAR USUÁRIO VIA SQLITE
         UsuarioDao usuario = new UsuarioDao(this, "App_InternetC");
         Usuario usuarioAutenticado = usuario.autenticarUsuario(getUsuario());
         usuario.close();

         if(usuarioAutenticado != null){
             Intent intent = new Intent();

             if(usuarioAutenticado.getTecnico().equals("sim")) {
                 intent = new Intent(getBaseContext(), Principal_TecnicoActivity.class);
             } else {
                 intent = new Intent(getBaseContext(), Principal_ClienteActivity.class);
             }
             intent.putExtra("usuario", usuarioAutenticado.getNome());
             startActivity(intent);
         } else {
             Toast.makeText(getBaseContext(),
                "Opa! Login ou senha invalido(s).",
             Toast.LENGTH_LONG).show();
         }
         */
     }

    public void Redirect_Cadastro(View view) {
        Intent intent = new Intent(getBaseContext(), Cadastro_UsuarioActivity.class);
        startActivity(intent);
    }

    public Usuario getUsuario() {
        Usuario usuario = new Usuario();
            usuario.setLogin(ed_Login.getText().toString());
            usuario.setSenha(ed_Senha.getText().toString());
        return usuario;
    }
}
