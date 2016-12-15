package br.ufsm.csi.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import br.ufsm.csi.R;
import br.ufsm.csi.model.Usuario;
import br.ufsm.csi.webAcess.AlterarUsuario;
import br.ufsm.csi.webAcess.CadastrarUsuario;

public class Alterar_UsuarioActivity extends AppCompatActivity {

    private long status;

    private EditText ed_login;
    private EditText ed_nome;
    private EditText ed_telefone;
    private EditText ed_senha;
    private EditText ed_endereco;
    private Switch sw_tecnico;
    private ImageView img_foto;
    private String caminhoFoto;
    private Boolean tecnico;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        Intent intent = getIntent();
        this.usuario = (Usuario) intent.getSerializableExtra("usuario");

        ed_login = (EditText) findViewById(R.id.ed_login_form);
        ed_nome = (EditText) findViewById(R.id.ed_nome_form);
        ed_telefone = (EditText) findViewById(R.id.ed_telefone_form);
        ed_senha = (EditText) findViewById(R.id.ed_senha_form);
        ed_endereco = (EditText) findViewById(R.id.ed_endereco_form);
        sw_tecnico = (Switch) findViewById(R.id.ed_tecnico);

        setUsuario();
    }

    public void AlterarUsuario(View view) {
        new AlterarUsuario(this, getUsuario()).execute();
    }

    public void setUsuario() {
        ed_login.setText(usuario.getLogin());
        ed_nome.setText(usuario.getNome());
        ed_telefone.setText(usuario.getTelefone());
        ed_senha.setText(usuario.getSenha());
        ed_endereco.setText(usuario.getEndereco());
    }

    public Usuario getUsuario() {
        Usuario usuario = new Usuario();
            usuario.setId(this.usuario.getId());
            usuario.setLogin(ed_login.getText().toString());
            usuario.setNome(ed_nome.getText().toString());
            usuario.setTelefone(ed_telefone.getText().toString());
            usuario.setSenha(ed_senha.getText().toString());
            usuario.setEndereco(ed_endereco.getText().toString());

            if(sw_tecnico.isChecked()) {
                usuario.setTecnico("sim");
            } else {
                usuario.setTecnico("nao");
            }
        return usuario;
    }
}
