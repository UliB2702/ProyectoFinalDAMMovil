package com.example.placegiver;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {

    RecyclerView rv;
    RecyclerView.LayoutManager miLayoutManager;
    AdaptadorPosts adaptador;
    APIRest api = new APIRest();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void recargarPosts() {
        api.obtenerPostsMasRecientes((success, posts) -> {
            if (success) {
                if (adaptador == null) {
                    adaptador = new AdaptadorPosts(posts, usuario ->{
                        Bundle bundle = new Bundle();
                        bundle.putString("usuario", usuario);

                        AccountFragment fragment = new AccountFragment();
                        fragment.setArguments(bundle);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container,  fragment)
                                .commit();
                    });
                    rv.setAdapter(adaptador);
                } else {
                    adaptador.setPosts(posts);
                    adaptador.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rvFeed);
        miLayoutManager = new GridLayoutManager(requireContext(), 1);
        rv.setLayoutManager(miLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        api.obtenerPostsMasRecientes((success, posts)->{
            if(success){
                adaptador = new AdaptadorPosts(posts, usuario ->{
                    Bundle bundle = new Bundle();
                    bundle.putString("usuario", usuario);

                    AccountFragment fragment = new AccountFragment();
                    fragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,  fragment)
                            .commit();
                });
                rv.setAdapter(adaptador);
            }
        });


    }
}