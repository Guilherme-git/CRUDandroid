package com.example.estoque.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.estoque.Adapters.AdapterProduto;
import com.example.estoque.Helper.ProdutoDAO;
import com.example.estoque.Helper.RecyclerItemClickListener;
import com.example.estoque.Model.Produto;
import com.example.estoque.R;

import java.util.ArrayList;
import java.util.List;

public class Pesquisar_produtos extends AppCompatActivity {

    RecyclerView recyclerProdutos;
    Button buttonPesquisar;
    List<Produto> listProduto = new ArrayList<>();
    AdapterProduto adapterProduto;

    Produto produtoSelecionado;
    EditText barraPesquisar;
    static boolean atualizar;
    ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_produtos);


        produtoDAO = new ProdutoDAO(this);
        buttonPesquisar = findViewById(R.id.buttonPesquisar);
        barraPesquisar = findViewById(R.id.BarraPesquisarProduto);

        //Configurando o Adapter de produtos//////////////////////////
        listProduto = produtoDAO.ListarProduto();
        adapterProduto = new AdapterProduto(listProduto,this);
        recyclerProdutos = findViewById(R.id.recyclerProdutos);
        recyclerProdutos.setHasFixedSize(true);
        recyclerProdutos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProdutos.setAdapter(adapterProduto);
        /////////////////////////////////////////////////////////////

        recyclerProdutos.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerProdutos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(Pesquisar_produtos.this,CadastrarProdutoActivity.class);
                intent.putExtra("produtoSelecionado", listProduto.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, final int position) {
                produtoSelecionado = listProduto.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(Pesquisar_produtos.this);
                dialog.setTitle("Confirmar exclusão");
                adapterProduto.remover(position);
                dialog.setMessage("Deseja realmente excluir "+produtoSelecionado.getNomeProduto()+" ?");


                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProdutoDAO produtoDAO = new ProdutoDAO(getApplicationContext());
                        if(produtoDAO.DeletarProduto(produtoSelecionado)){

                            Toast.makeText(getApplicationContext(),"Sucesso ao excluir produto!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Erro ao excluir produto!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.setNegativeButton("Não", null);
                dialog.create();
                dialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

        buttonPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pesquisa = barraPesquisar.getText().toString();

                adapterProduto.mudaLista(Pesquisar(pesquisa));

                if(barraPesquisar.getText().equals("")){
                    adapterProduto.mudaLista(listProduto);
                }
            }
        });

    }

    public List<Produto> Pesquisar(String pesquisa){
        ArrayList<Produto> pesquisaProduto = new ArrayList(listProduto);
        for(int i=0;i< pesquisaProduto.size();i++){
            if(! pesquisaProduto.get(i).getNomeProduto().contains(pesquisa)){
                pesquisaProduto.remove(i);
            }
        }
        return pesquisaProduto;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(atualizar){
            listProduto =produtoDAO.ListarProduto();
            adapterProduto.mudaLista(listProduto);
            atualizar = false;
        }

    }
}
