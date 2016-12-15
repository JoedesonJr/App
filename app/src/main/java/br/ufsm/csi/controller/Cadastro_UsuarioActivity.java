
package br.ufsm.csi.controller;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.Image;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Switch;
        import android.widget.Toast;

        import com.google.android.gms.ads.formats.NativeAd;

        import java.io.ByteArrayOutputStream;
        import java.io.File;

        import br.ufsm.csi.R;
        import br.ufsm.csi.dao.UsuarioDao;
        import br.ufsm.csi.model.Usuario;
        import br.ufsm.csi.webAcess.CadastrarUsuario;

public class Cadastro_UsuarioActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ed_login = (EditText) findViewById(R.id.ed_login_form);
        ed_nome = (EditText) findViewById(R.id.ed_nome_form);
        ed_telefone = (EditText) findViewById(R.id.ed_telefone_form);
        ed_senha = (EditText) findViewById(R.id.ed_senha_form);
        ed_endereco = (EditText) findViewById(R.id.ed_endereco_form);
        sw_tecnico = (Switch) findViewById(R.id.ed_tecnico);
        img_foto = (ImageView) findViewById(R.id.img_foto);

        img_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                /*
                caminhoFoto = Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DCIM).toString()
                        + "/" +System.currentTimeMillis()+ ".png";

                // diretorio de onde esta a foto mais o seu nome
                caminhoFoto = Environment.getExternalStorageDirectory().toString()
                    + "/" +System.currentTimeMillis()+ ".png";

                Log.d("u", "caminho:   " + caminhoFoto);

                File foto = new File(caminhoFoto);
                Uri localImagem = Uri.fromFile(foto);

                camera.putExtra(MediaStore.EXTRA_OUTPUT, localImagem);
                */
                startActivityForResult(camera, 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img_foto.setImageBitmap(photo);
        }
    }

    public void CadastrarUsuario(View view) {
        new CadastrarUsuario(this, getUsuario()).execute();

        /* CADASTRAR USUARIO VIA SQLITE
        UsuarioDao cadastrarUsuario = new UsuarioDao(this, "App_InternetC");
        this.status = cadastrarUsuario.insert(getUsuario());
        cadastrarUsuario.close();

        if(this.status > 0){
            Toast.makeText(getBaseContext(),
                "Ok! Usuário cadastrado com sucesso.",
            Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getBaseContext(),
                "Opa! Usuário não foi cadastrado. Tente novamente mais tarde.",
            Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        */
    }

    public Usuario getUsuario() {
        Usuario usuario = new Usuario();
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
