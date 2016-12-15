package br.ufsm.csi.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.ufsm.csi.R;
import br.ufsm.csi.dao.UsuarioDao;
import br.ufsm.csi.model.Usuario;
import br.ufsm.csi.webAcess.AutenticarUsuario;
import br.ufsm.csi.webAcess.BuscarUsuario;
import br.ufsm.csi.webAcess.ListarUsuarios;

import static android.widget.AdapterView.*;

public class Principal_ClienteActivity extends AppCompatActivity {

    private TextView boas_vindas;
    private ListView lista;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_cliente);

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("usuario");

        lista = (ListView) findViewById(R.id.usuarios);
        boas_vindas = (TextView) findViewById(R.id.pagina_principal);
        boas_vindas.setText("Técnicos disponíveis");

        getUsuarios();

        registerForContextMenu(lista);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem ligar = menu.add("Ligar para o Técnico");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + usuario.getTelefone()));

                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    startActivity(callIntent);

                    return true;
                }

                startActivity(callIntent);

                return false;
            }
        });

        MenuItem mapa = menu.add("Ver no Mapa");
        mapa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Principal_ClienteActivity.this, Mapa_ClienteActivity.class);
                intent.putExtra("aluno", usuario);
                startActivity(intent);

                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void getUsuarios() {

        new ListarUsuarios(this, lista).execute();

        /*  LISTA DE USUARIOS COM SQLITE
        UsuarioDao dao = new UsuarioDao(this, "App_InternetC");

        ArrayAdapter<Usuario> adapter
            = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, dao.getUsuarios());

        lista.setAdapter(adapter);
        */
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mapa:
                Intent i = new Intent(Principal_ClienteActivity.this, Mapa_ClienteActivity.class);
                startActivity(i);
            break;

            case R.id.alterarDados:
                new BuscarUsuario(this, this.usuario).execute();
            break;

            case R.id.verChamadas:  // IMPLEMENTAR

            break;

            case R.id.sair:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}
