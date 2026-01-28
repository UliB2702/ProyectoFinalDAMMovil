package com.example.placegiver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorPosts extends RecyclerView.Adapter<AdaptadorPosts.MyViewHolder>{

    int selectedPos = RecyclerView.NO_POSITION;

    public AdaptadorPosts(){

    }

    public int getSelectedPos(){
        return selectedPos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        MyViewHolder mvh = new MyViewHolder(elemento);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombreUsuario;
        TextView txtTexto;
        ImageView imgUsuario;
        ImageView imgAdjunto;
        Button btnLike, btnComentarios;

        public MyViewHolder(View viewElemento){
            super(viewElemento);

            this.txtNombreUsuario = viewElemento.findViewById(R.id.txtUserName);
            this.txtTexto = viewElemento.findViewById(R.id.txtMensaje);
            this.imgUsuario = viewElemento.findViewById(R.id.imgFotoUsuario);
            this.imgAdjunto = viewElemento.findViewById(R.id.imgAdjunto);
            this.btnLike = viewElemento.findViewById(R.id.btnLikes);
            this.btnComentarios = viewElemento.findViewById(R.id.btnComentarios);

            txtTexto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            imgAdjunto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            imgUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            txtNombreUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }
}
