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
    private String usuarioSesion;
    public interface OnUsuarioClickListener {
        void onUsuarioClick(String usuario);
    }
    public interface OnPostDeleteListener {
        void onPostDelete(Post post, int position);
    }

    private OnUsuarioClickListener listener;
    private OnPostDeleteListener deleteListener;

    /**
     * Sets the data for the posts' adapter
     * @param posts Posts that needs to appear in the RecycleView
     * @param usuarioSesion Current name of the user logged in
     * @param listener Event for when the username or profile picture is clicked
     * @param deleteListener Event for when the delete post button is clicked
     * */
    public AdaptadorPosts(ArrayList<Post> posts, String usuarioSesion,
                          OnUsuarioClickListener listener,
                          OnPostDeleteListener deleteListener){
        this.posts = posts;
        this.listener = listener;
        this.usuarioSesion = usuarioSesion;
        this.deleteListener = deleteListener;

    }

    /**
     * Sets the posts for the adapter
     * @param  posts List of posts for the adapter
     * */
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    /**
     * Returns the post that was clicked from the adapter
     * @return The id of the post that was selected
     * */
    public int getSelectedPos(){
        return selectedPos;
    }

    /**
     * Deletes a posts from the adapter
     * */
    public void eliminarPost(int position){
        posts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, posts.size());
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
        holder.ponerId(p.getId());
        if(p.getUsuario().equals(usuarioSesion)){
            holder.btnBorrar.setVisibility(View.VISIBLE);
        } else {
            holder.btnBorrar.setVisibility(View.GONE);
        }
        holder.imgAdjunto.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    /**
     * Creates a container for each posts of the adapter
     *
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombreUsuario;
        TextView txtTexto;
        ImageView imgUsuario;
        ImageView imgAdjunto;
        Button btnLike, btnComentarios, btnBorrar;

        int id;

        public MyViewHolder(View viewElemento){
            super(viewElemento);

            this.txtNombreUsuario = viewElemento.findViewById(R.id.txtUserName);
            this.txtTexto = viewElemento.findViewById(R.id.txtMensaje);
            this.imgUsuario = viewElemento.findViewById(R.id.imgFotoUsuario);
            this.imgAdjunto = viewElemento.findViewById(R.id.imgAdjunto);
            this.btnLike = viewElemento.findViewById(R.id.btnLikes);
            this.btnComentarios = viewElemento.findViewById(R.id.btnComentarios);
            this.btnBorrar = viewElemento.findViewById(R.id.btnBorrar);




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

            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        deleteListener.onPostDelete(posts.get(position), position);
                    }
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

        /**
         * Return the TextView with the user's name who created to post
         * @return TextView of the user's name
         * */
        public TextView obtenerNombreUsuario(){
            return txtNombreUsuario;
        }

        /**
         * Return the TextView with the post's text
         *  @return TextView of the post's text
         * */
        public TextView obtenerTexto(){
            return txtTexto;
        }

        /**
         * Return the Id of the Element
         * @return Id of the Element
         * */
        public int obtenerId(){return id;}


        /**
         * Return the TextView with the post's text
         * @param id Sets a new Id for the Element
         * */
        public void ponerId(int id){
            this.id = id;
        }
    }
}
