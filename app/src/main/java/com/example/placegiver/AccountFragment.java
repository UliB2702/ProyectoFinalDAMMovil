package com.example.placegiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AccountFragment extends Fragment {

    Toolbar tb;
    RecyclerView rv;
    RecyclerView.LayoutManager miLayoutManager;
    ActivityResultLauncher<Intent> launcherPost;
    AdaptadorPosts adaptadorPosts;
    EditText edtEscribirPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_perfil_usuario, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.perfil_usuario), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tb = view.findViewById(R.id.toolbar4);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(tb);

        ActionBar ab = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        tb.setTitle("");

        setHasOptionsMenu(true);

        rv = view.findViewById(R.id.rvPostsUsuario);
        miLayoutManager = new GridLayoutManager(requireContext(), 1);
        rv.setLayoutManager(miLayoutManager);

        adaptadorPosts = new AdaptadorPosts((new APIRest().obtenerPostsDeUsuario());
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
