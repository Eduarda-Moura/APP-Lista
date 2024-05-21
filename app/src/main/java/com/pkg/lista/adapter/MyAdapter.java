package com.pkg.lista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pkg.lista.activity.MainActivity;
import com.pkg.lista.model.MyItem;

import java.util.List;

import moura.eduarda.erick.applista.R;

//Constrói e preenche a interface
public class MyAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens){
        this.mainActivity = mainActivity;
        this.itens = itens;
    }
    //Classe container que armazena a interface
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
    //Cria os elementos de interface para um item
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.item_list,parent,false);
        return new MyViewHolder(v);
    }

    //Preenche a interface (criada pela função acima) com os dados de um item
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        //Indica qual elemento deve ser usado para preencher o item
        MyItem myItem = itens.get(position);
        //Objeto que guarda os itens de interface criados na função supracriada
        View v = holder.itemView;

        // Obtém a imagem
        ImageView imvfoto = v.findViewById(R.id.imvPhoto);
        imvfoto.setImageBitmap(myItem.photo);

        // Obtém o texto
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        // Obtém a descrição
        TextView tvDesc = v.findViewById(R.id.tvDesc);
        tvDesc.setText(myItem.description);

    }

    public int getItemCount(){
        return itens.size();
    }


}
