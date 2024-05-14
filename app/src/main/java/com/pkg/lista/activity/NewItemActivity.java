package com.pkg.lista.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import moura.eduarda.erick.applista.R;

public class NewItemActivity extends AppCompatActivity {

    // Codigo para a solicitação do seletor de fotos
    static int PHOTO_PICKER_REQUEST = 1;
    // Uri para a foto selecionada
    Uri photoSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        // Configura o clique do botão de seleção de foto
        ImageButton imgCl = findViewById(R.id.imbCl);
        imgCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a galeria de fotos para selecionar uma imagem
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });

        // Configura o clique do botão de adicionar item
        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se uma foto foi selecionada
                if (photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }

                // Obtém o título digitado
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();

                // Verifica se o título foi inserido
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }

                // Obtém a descrição digitada
                EditText etDesc = findViewById(R.id.etDesc);
                String desc = etDesc.getText().toString();

                // Verifica se a descrição foi inserida
                if (desc.isEmpty()){
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }

                // Cria um Intent para retornar os dados do novo item para a MainActivity
                Intent i = new Intent();
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("desc", desc);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Verifica se o resultado foi recebido do seletor de fotos
        if(requestCode == PHOTO_PICKER_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                // Obtém a Uri (endereço) da foto selecionada e exibe
                photoSelected = data.getData();
                ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
                imvfotoPreview.setImageURI(photoSelected);
            }
        }
    }
}
