package com.pkg.lista.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pkg.lista.adapter.MyAdapter;
import com.pkg.lista.model.MainActivityViewModel;
import com.pkg.lista.model.MyItem;
import com.pkg.lista.util.Util;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import moura.eduarda.erick.applista.R;

public class MainActivity extends AppCompatActivity {
    //Código do intent
    static int NEW_ITEM_REQUEST = 1;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Acha o botão de adicionar item
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        // Configura o cliquedo botão de adicionar item
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia a intenção de adicionar novo item
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });

        // obtém o RecycleView
        RecyclerView rvItens = findViewById(R.id.rvItens);

        MainActivityViewModel vm = new ViewModelProvider( this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();

        //Cria e seta o myAdapter no RecycleView (lista)
        myAdapter = new MyAdapter(this,itens);
        rvItens.setAdapter(myAdapter);
        // Indica que há variação de tamanho na lista
        rvItens.setHasFixedSize(true);

        // Configura o layout da lista
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        // Adiciona linha entre os itens da lista
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK){
                // Adiciona um novo item com as informações recebidas (input)
                MyItem myItem = new MyItem();

                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("desc");
                Uri selectedPhotoURI = data.getData();

                // carrega a imagem e guarda dentro de um Bitman
                try{
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100, 100);
                    myItem.photo = photo;
                }
                // Uma execção é disparada caso o arquivo não seja encontrado
                catch(FileNotFoundException e) {
                    e.printStackTrace();
                }

                MainActivityViewModel vm = new ViewModelProvider( this).get(MainActivityViewModel.class);
                List<MyItem> itens = vm.getItens();

                // Adiciona o novo item à lista
                itens.add(myItem);
                // Notifica a classe MyAdapter
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }


}