package com.example.placegiver;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AccountFragment extends Fragment {
    private String usuarioMostrado;
    RecyclerView rv;
    RecyclerView.LayoutManager miLayoutManager;
    ActivityResultLauncher<Intent> launcherPost;
    AdaptadorPosts adaptadorPosts;

    Button btnEnviarPost;
    TextView tvNombre, tvDescripcion;
    AdaptadorPosts adaptador;
    APIRest api = new APIRest();

    EditText edtEscribirPost;

    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);

    }
    public String getUsuarioMostrado() {
        return usuarioMostrado;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        usuarioMostrado = getArguments().getString("usuario");
        prefs = requireActivity().getSharedPreferences("sesion", MODE_PRIVATE);
        tvNombre = view.findViewById(R.id.txtUsuario);
        tvDescripcion = view.findViewById(R.id.txtDescripcion);
        rv = view.findViewById(R.id.rvPostsUsuario);
        btnEnviarPost = view.findViewById(R.id.btnEnvioPost);
        miLayoutManager = new GridLayoutManager(requireContext(), 1);
        rv.setLayoutManager(miLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        edtEscribirPost = view.findViewById(R.id.edtEscrbirPost);
        String usuarioActual = prefs.getString("nombre", "");
        if(!prefs.contains("nombre") || !getArguments().getString("usuario").equals(usuarioActual)){
            edtEscribirPost.setVisibility(View.GONE);
            btnEnviarPost.setVisibility(View.GONE);
        }

        String usuarioAVerNombre = getArguments().getString("usuario");
        new APIRest().obtenerDatosUsuario(usuarioAVerNombre, ((success, u) -> {
            requireActivity().runOnUiThread(() -> {
                if (success) {
                    tvNombre.setText(u.getNombre());
                    tvDescripcion.setText(u.getDescripcion());
                    Log.i("Hola", u.getDescripcion() == null ? "Si" : "No");
                    if (u.getDescripcion() == null) {
                        tvDescripcion.setText("");
                    } else {
                        tvDescripcion.setText(u.getDescripcion());
                    }

                }
            });
        }));

        btnEnviarPost.setOnClickListener(v -> {
            String texto = edtEscribirPost.getText().toString().trim();

            if (!texto.isEmpty()) {

                api.crearPost(texto, tvNombre.getText().toString(), success -> {
                    if (success) {
                        requireActivity().runOnUiThread(() -> {
                            edtEscribirPost.setText("");
                            cargarPosts(tvNombre.getText().toString());
                        });
                    }
                });
            }
        });

        cargarPosts(usuarioAVerNombre);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu2, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            requireActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarPosts(String usuario) {
        String usuarioActual = prefs.getString("nombre", "");
        api.obtenerPostsDeUsuario(usuario, (success, posts) -> {
            requireActivity().runOnUiThread(() -> {
                if (success) {
                    adaptador = new AdaptadorPosts(posts, usuarioActual, usuarioClick ->{
                        Bundle bundle = new Bundle();
                        bundle.putString("usuario", usuario);

                        AccountFragment fragment = new AccountFragment();
                        fragment.setArguments(bundle);

                        ((MainActivity) requireActivity()).navegarA(fragment, true);
                    });

                    rv.setAdapter(adaptador);
                }
            });
        });
    }
}
