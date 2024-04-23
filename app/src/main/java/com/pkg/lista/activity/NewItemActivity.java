package com.pkg.lista.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import moura.eduarda.erick.applista.R;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;
    Uri photoSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        ImageButton imgCl = findViewById(R.id.imbCl);
        imgCl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);

                if (photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etDesc = findViewById(R.id.etDesc);
                String desc = etDesc.getText().toString();

                if (desc.isEmpty()){
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }

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
        if(requestCode == PHOTO_PICKER_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                photoSelected = data.getData();
                ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
                imvfotoPreview.setImageURI(photoSelected);
            }
        }
    }
}