package br.ufsm.csi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufsm.csi.model.Usuario;

public class UsuarioDao extends SQLiteOpenHelper {

    private String sql;

    public UsuarioDao(Context context, String nameDB) {
        super(context, nameDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.sql = ""
            + " CREATE TABLE usuario ( "
            +       " id SERIAL PRIMARY KEY, "
            +       " login TEXT NOT NULL, "
            +       " nome TEXT NOT NULL, "
            +       " email TEXT NOT NULL, "
            +       " senha TEXT NOT NULL, " +
                    " tecnico TEXT NOT NULL "
            + " ); ";

        db.execSQL(sql);
    }


    public Usuario autenticarUsuario(Usuario usuario) {

        SQLiteDatabase db = getReadableDatabase();

        this.sql = " SELECT nome, tecnico FROM usuario WHERE login = ? AND senha = ?; ";
        Cursor cursor = db.rawQuery(sql,
            new String[] {usuario.getLogin(), usuario.getSenha()});

        while(cursor.moveToNext()){
            Log.d("appInternetC", "Nome: " +cursor.getString(cursor.getColumnIndex("nome")));
            Log.d("appInternetC", "Tec: " +cursor.getString(cursor.getColumnIndex("tecnico")));
            Usuario usuarioAutenticado = new Usuario();
                usuarioAutenticado.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                usuarioAutenticado.setTecnico(cursor.getString(cursor.getColumnIndex("tecnico")));
            return usuarioAutenticado;
        }

        return null;
    }

    public long insert(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
            cv.put("login", usuario.getLogin());
            cv.put("nome", usuario.getNome());
            cv.put("email", usuario.getTelefone());
            cv.put("senha", usuario.getSenha());
            cv.put("tecnico", usuario.getTecnico());
        return db.insert("Usuario", null, cv);
    }

    public ArrayList<Usuario> getUsuarios() {

        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        SQLiteDatabase db = getReadableDatabase();

        this.sql = " SELECT * FROM usuario; ";
        Cursor cursor = db.rawQuery(this.sql, null);

        while(cursor.moveToNext()){
            Usuario usuario = new Usuario();
            Log.d("appInternetC", "Nome: " +cursor.getString(cursor.getColumnIndex("nome")));
                usuario.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                usuario.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            usuarios.add(usuario);
        }

        return usuarios;
    }

    public ArrayList<Usuario> getUsuario() {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        Usuario usuario = new Usuario();
            usuario.setNome("Joao");
            usuario.setTelefone("9090999880288");
            usuario.setEndereco("Rua osvaldo aranha 109 santa maria");
        usuarios.add(usuario);

        return usuarios;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
