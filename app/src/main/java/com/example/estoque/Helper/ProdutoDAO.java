package com.example.estoque.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.estoque.Model.Categoria;
import com.example.estoque.Model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private SQLiteDatabase insert;
    private SQLiteDatabase ler;
  // List<Produto> listProduto = new ArrayList<>();

    public ProdutoDAO(Context context) {
        DBhelper db = new DBhelper(context);
        insert = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    public boolean SalvarProduto(Produto produto){

        ContentValues cv = new ContentValues();
        cv.put("nome_produto" , produto.getNomeProduto());
        cv.put("quantidade_produto", produto.getQuantidadeProduto());
        cv.put("preco_produto", produto.getPrecoProduto());
        cv.put("custo_produto",produto.getCustoProduto());
        cv.put("categoria_id",produto.getCategoria());

        try{
            insert.insert(DBhelper.TABELA_PRODUTO,null,cv);
            Log.e("INFO","Produto salvo");
        }catch (Exception e){
            Log.e("INFO","Erro ao salvar produto "+e.getMessage());
            return false;
        }
        return true;
    }

    public boolean AtualizarProduto(Produto produto){

        ContentValues cv = new ContentValues();

        cv.put("nome_produto" , produto.getNomeProduto());
        cv.put("quantidade_produto", produto.getQuantidadeProduto());
        cv.put("preco_produto", produto.getPrecoProduto());
        cv.put("custo_produto",produto.getCustoProduto());
        cv.put("categoria_id",produto.getCategoria());

        try{
            String[] args = {produto.getIdProduto()+""};
            insert.update(DBhelper.TABELA_PRODUTO, cv, "id_produto=?",args);
            Log.e("INFO","Produto atualizado");
        }catch (Exception e){
            Log.e("INFO","Erro ao atualizar produto "+e.getMessage());
            return false;
        }
        return true;
    }



    public boolean DeletarProduto(Produto produto){
        try{
            String[] args = {String.valueOf(produto.getIdProduto())};
            insert.delete(DBhelper.TABELA_PRODUTO, " id_produto=?", args);
            Log.e("INFO","Produto removido com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao deletar produto "+e.getMessage());
            return false;
        }
        return true;
    }

    public List<Produto> ListarProduto(){
        String sql = "SELECT * FROM "+DBhelper.TABELA_PRODUTO+" ;";
        Cursor c = ler.rawQuery(sql,null);

        List<Produto> list = new ArrayList<>();

        while (c.moveToNext()){
            Produto produto= new Produto();

            int id = c.getInt(c.getColumnIndex("id_produto"));
            String nome = c.getString(c.getColumnIndex("nome_produto"));
            int categoria = c.getInt(c.getColumnIndex("categoria_id"));
            double preco = c.getDouble(c.getColumnIndex("preco_produto"));
            double custo = c.getDouble(c.getColumnIndex("custo_produto"));
            int quantidade = c.getInt(c.getColumnIndex("quantidade_produto"));

            produto.setIdProduto(id);
            produto.setNomeProduto(nome);
            produto.setPrecoProduto(preco);
            produto.setCustoProduto(custo);
            produto.setQuantidadeProduto(quantidade);
            produto.setCategoria(categoria);

            list.add(produto);
        }
        return list;
    }




}
