package com.example.estoque.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.estoque.Model.Categoria;
import com.example.estoque.Model.Produto;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO{

    private SQLiteDatabase insert;
    private SQLiteDatabase ler;

    public CategoriaDAO(Context context) {
        DBhelper db = new DBhelper(context);

        insert = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    
    public boolean SalvarCategoria(Categoria categoria) {
        ContentValues cv = new ContentValues();
        cv.put("nome_categoria", categoria.getNomeCategoria());

        try{
            insert.insert(DBhelper.TABELA_CATEGORIA,null, cv);
            Log.e("INFO","Categoria salva");
        }catch (Exception e){
            Log.e("INFO","Erro ao salvar categoria "+e.getMessage());
            return false;
        }
        return true;
    }

    public List<Categoria> ListaCategoria() {
        List<Categoria> listCategoria  = new ArrayList<>();
        String sql = "SELECT * FROM "+DBhelper.TABELA_CATEGORIA+" ;";
        Cursor c = ler.rawQuery(sql,null);

        while (c.moveToNext()){
            Categoria categoria = new Categoria();

            int id = c.getInt(c.getColumnIndex("id_categoria"));
            String nome = c.getString(c.getColumnIndex("nome_categoria"));

            categoria.setIdCategoria(id);
            categoria.setNomeCategoria(nome);

            listCategoria.add(categoria);
        }
        return listCategoria;
    }
}
