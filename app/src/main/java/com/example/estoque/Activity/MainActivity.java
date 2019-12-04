package com.example.estoque.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estoque.Helper.CategoriaDAO;
import com.example.estoque.Model.Categoria;
import com.example.estoque.R;

public class MainActivity extends AppCompatActivity {

    Categoria categoriaModel = new Categoria();
    Button mostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SalvarCategorias();
        mostrar = findViewById(R.id.mostrar);

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),  Pesquisar_produtos.class);
                startActivity(intent);
            }
        });
    }


    public void SalvarCategorias(){
        //categoriaModel.setNomeCategoria("Bebidas");
        //categoriaModel.setNomeCategoria("Esportes");
        //categoriaModel.setNomeCategoria("Brinquedos");
        //categoriaModel.setNomeCategoria("Acessórios");
        CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());
        categoriaDAO.SalvarCategoria(categoriaModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cadastrar_produto:
                Intent intent = new Intent(this, CadastrarProdutoActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
