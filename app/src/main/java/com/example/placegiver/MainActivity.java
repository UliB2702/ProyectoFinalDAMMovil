package com.example.placegiver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toogle;
    Toolbar tb;
    private SharedPreferences prefs;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }


        tb = findViewById(R.id.toolbar_main);
        setSupportActionBar(tb);

        drawer = findViewById(R.id.mainDrawer);
        navigationView = findViewById(R.id.nav_view);
        prefs = getSharedPreferences("sesion", MODE_PRIVATE);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayUseLogoEnabled(true);
        ab.setLogo(R.drawable.logo);
        ab.setTitle("");

        toogle = new ActionBarDrawerToggle(this, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);


    }

    private void navegarDesdeDrawer(Fragment fragment) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            if (fragment instanceof HomeFragment) {
                ((HomeFragment) fragment).recargarPosts();
            }
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        getSupportFragmentManager().popBackStack(
                null,
                getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE
        );
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        drawer.closeDrawer(GravityCompat.START);
    }
    private void actualizarMenuDrawer() {
        if (navigationView == null) return;

        boolean sesionIniciada = prefs.getBoolean("login", false);

        navigationView.getMenu().clear();
        View header = navigationView.getHeaderView(0);
        TextView tvNombre = header.findViewById(R.id.nav_header_tvnombre);
        TextView tvCorreo = header.findViewById(R.id.nav_header_tvemail);
        if (sesionIniciada && prefs.contains("nombre") && prefs.contains("email")) {
            navigationView.inflateMenu(R.menu.menu_sesion_iniciada);
            tvNombre.setText(prefs.getString("nombre", "Usuario"));
            tvCorreo.setText(prefs.getString("email", ""));
        } else {
            navigationView.inflateMenu(R.menu.menu_sin_sesion);
            tvNombre.setText("Usuario");
            tvCorreo.setText("ejemplo@gmail.com");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        actualizarMenuDrawer();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btn_iniciarSesion){
            startActivity(new Intent(MainActivity.this, IniciarSesionActivity.class));
        } else if (item.getItemId() == R.id.btn_crear) {
            startActivity(new Intent(MainActivity.this, RegistroActivity.class));
        }
        else if(item.getItemId() == R.id.btn_menu_inicio){
            navegarDesdeDrawer(new HomeFragment());
        }
        else if(item.getItemId() == R.id.btn_menu_cuenta){
            String nombre = prefs.getString("nombre", "Usuario");
            Log.i("Hola", nombre);
            Bundle bundle = new Bundle();
            bundle.putString("usuario", nombre);

            AccountFragment fragment = new AccountFragment();
            fragment.setArguments(bundle);

            navegarDesdeDrawer(fragment);
        }
        else if(item.getItemId() == R.id.btn_menu_cerrarSesion){

            prefs.edit().clear().apply();
            actualizarMenuDrawer();
            navegarDesdeDrawer(new HomeFragment());
            Toast.makeText(getApplicationContext(), "Sesión cerrada",Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toogle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toogle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}