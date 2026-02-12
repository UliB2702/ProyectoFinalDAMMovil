package com.example.placegiver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorPosts extends RecyclerView.Adapter<AdaptadorPosts.MyViewHolder>{

    int selectedPos = RecyclerView.NO_POSITION;
    ArrayList<Post> posts = new ArrayList<Post>();
    public interface OnUsuarioClickListener {
        void onUsuarioClick(String usuario);
    }

    private OnUsuarioClickListener listener;

    public AdaptadorPosts(ArrayList<Post> posts, OnUsuarioClickListener listener){
        this.posts = posts;
        this.listener = listener;
    }
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
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
        Post p = this.posts.get(position);
        holder.obtenerTexto().setText(p.getTexto());
        holder.obtenerNombreUsuario().setText(p.getUsuario());
    }

    @Override
    public int getItemCount() {
        return posts.size();
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
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onUsuarioClick(posts.get(position).getUsuario());
                    }
                }
            });

            txtNombreUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onUsuarioClick(posts.get(position).getUsuario());
                    }

                }
            });

        }

        public TextView obtenerNombreUsuario(){
            return txtNombreUsuario;
        }
        public TextView obtenerTexto(){
            return txtTexto;
        }


    }
}
