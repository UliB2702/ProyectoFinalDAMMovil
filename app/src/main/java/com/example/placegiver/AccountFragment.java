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

    RecyclerView rv;
    RecyclerView.LayoutManager miLayoutManager;
    ActivityResultLauncher<Intent> launcherPost;
    AdaptadorPosts adaptadorPosts;

    TextView tvNombre, tvDescripcion;


    EditText edtEscribirPost;

    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNombre = view.findViewById(R.id.txtUsuario);
        tvDescripcion = view.findViewById(R.id.txtDescripcion);
        rv = view.findViewById(R.id.rvPostsUsuario);
        miLayoutManager = new GridLayoutManager(requireContext(), 1);
        rv.setLayoutManager(miLayoutManager);

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

        //adaptadorPosts = new AdaptadorPosts((new APIRest().obtenerPostsDeUsuario());
        rv.addItemDecoration(
                new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
        );
        rv.setAdapter(adaptadorPosts);

        edtEscribirPost = view.findViewById(R.id.edtEscrbirPost);
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
}
