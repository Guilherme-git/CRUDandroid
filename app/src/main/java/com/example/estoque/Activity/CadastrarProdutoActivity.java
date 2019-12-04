package com.example.estoque.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estoque.Helper.CategoriaDAO;
import com.example.estoque.Helper.ProdutoDAO;
import com.example.estoque.Model.Categoria;
import com.example.estoque.Model.Produto;
import com.example.estoque.R;

import java.util.List;

public class CadastrarProdutoActivity extends AppCompatActivity {

    TextView titulo;
    EditText nomeProduto;
    EditText precoProduto;
    EditText quantidadeProduto;
    EditText custoProduto;
    Spinner categoriaProduto;
    Button salvar;

    List<Categoria> listacategoria;
    Produto produtoAtual = new Produto();
    ProdutoDAO produtoDAO;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);

        InicializaComponentes();
        ListaCategorias();


        produtoAtual = (Produto) getIntent().getSerializableExtra("produtoSelecionado");
        produtoDAO = new ProdutoDAO(getApplicationContext());

        if(produtoAtual != null){
            titulo.setText("Editar Produto");
            nomeProduto.setText(produtoAtual.getNomeProduto());
            precoProduto.setText(String.valueOf(produtoAtual.getPrecoProduto()));
            quantidadeProduto.setText(String.valueOf(produtoAtual.getQuantidadeProduto()));
            custoProduto.setText(String.valueOf(produtoAtual.getCustoProduto()));


            Categoria categoriaProcurada = new Categoria();
            categoriaProcurada.setIdCategoria(produtoAtual.getCategoria());
            int idCat= listacategoria.indexOf(categoriaProcurada);
            categoriaProduto.setSelection(idCat);
            salvar.setText("Editar");

        }

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(produtoAtual == null){
                    if (nomeProduto.getText().toString().equals("") || precoProduto.getText().toString().equals("") || custoProduto.getText().toString().equals("") ||  quantidadeProduto.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),"Preencha todos os campos",Toast.LENGTH_SHORT);
                    }else{
                        Produto produto = new Produto();
                        produto.setNomeProduto(nomeProduto.getText().toString());
                        produto.setQuantidadeProduto(Integer.parseInt(String.valueOf(quantidadeProduto.getText())));
                        produto.setCategoria(listacategoria.get((int)categoriaProduto.getSelectedItemId()).getIdCategoria());
                        produto.setPrecoProduto(Double.parseDouble(precoProduto.getText().toString()));
                        produto.setCustoProduto(Double.parseDouble(custoProduto.getText().toString()));
                        if(produtoDAO.SalvarProduto(produto) == true){
                            Toast.makeText(getApplicationContext(), "Produto salvo", Toast.LENGTH_SHORT);
                            Pesquisar_produtos.atualizar = true;
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao salvar", Toast.LENGTH_SHORT);
                        }
                    }
                }else{
                    if (nomeProduto.getText().toString().equals("") || precoProduto.getText().toString().equals("") || custoProduto.getText().toString().equals("") || quantidadeProduto.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),"Preencha todos os campos",Toast.LENGTH_SHORT);
                    }else{
                        Produto produto = new Produto();
                        produto.setIdProduto(produtoAtual.getIdProduto());
                        produto.setNomeProduto(nomeProduto.getText().toString());
                        produto.setQuantidadeProduto(Integer.parseInt(quantidadeProduto.getText().toString()));
                        produto.setCategoria(listacategoria.get((int)categoriaProduto.getSelectedItemId()).getIdCategoria());
                        produto.setPrecoProduto(Double.parseDouble(precoProduto.getText().toString()));
                        produto.setCustoProduto(Double.parseDouble(custoProduto.getText().toString()));
                        if(produtoDAO.AtualizarProduto(produto) == true){
                            Toast.makeText(getApplicationContext(), "Produto editado", Toast.LENGTH_SHORT);
                            Pesquisar_produtos.atualizar = true;
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao editar", Toast.LENGTH_SHORT);
                        }
                    }
                }
            }
        });
    }

    public void ListaCategorias(){
        CategoriaDAO categoriaDAO = new CategoriaDAO(getApplicationContext());
        listacategoria = categoriaDAO.ListaCategoria();

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listacategoria);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaProduto.setAdapter(spinnerAdapter);
    }

    public void InicializaComponentes(){
        titulo = findViewById(R.id.textViewTitulo);
        nomeProduto = findViewById(R.id.nomeProduto);
        precoProduto = findViewById(R.id.precoProduto);
        quantidadeProduto = findViewById(R.id.quantidadeProduto);
        categoriaProduto = findViewById(R.id.categoriaProduto);
        custoProduto = findViewById(R.id.custoProduto);
        salvar = findViewById(R.id.salvar);
    }


}
