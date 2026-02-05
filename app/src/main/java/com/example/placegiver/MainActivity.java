package com.example.placegiver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toogle;
    Toolbar tb;
    private SharedPreferences prefs;
    private NavigationView navigationView;
    ActivityResultLauncher<Intent> launcher, launcher2;
    Intent i;
    Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = getIntent();



        tb = findViewById(R.id.toolbar_main);
        setSupportActionBar(tb);

        drawer = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);
        prefs = getSharedPreferences("sesion", MODE_PRIVATE);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        toogle = new ActionBarDrawerToggle(this, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (navigationView == null) return;

        boolean sesionIniciada = prefs.getBoolean("login", false);
        Log.d("MAIN", "Sesion iniciada: " + sesionIniciada);

        navigationView.getMenu().clear();

        if (sesionIniciada && prefs.contains("nombre") && prefs.contains("email")) {
            View header = navigationView.getHeaderView(0);
            TextView tvNombre = header.findViewById(R.id.nav_header_tvnombre);
            TextView tvCorreo = header.findViewById(R.id.nav_header_tvemail);


            navigationView.inflateMenu(R.menu.menu_sesion_iniciada);
            u = (Usuario) (i.getSerializableExtra("usuario"));

            tvNombre.setText(prefs.getString("nombre", "Usuario"));
            tvCorreo.setText(prefs.getString("email", ""));

            Datos.setU(u);
        } else {
            navigationView.inflateMenu(R.menu.menu_sin_sesion);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btn_iniciarSesion){
            Intent i = new Intent(MainActivity.this, IniciarSesionActivity.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.btn_crear) {
            Intent i = new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(i);
        }
        else if(item.getItemId() == R.id.btn_menu_cerrarSesion){
            Toast.makeText(getApplicationContext(), "Sesión cerrada",
                    Toast.LENGTH_LONG);
            prefs.edit().clear().apply();
            recreate();

        }
        drawer.closeDrawer(GravityCompat.START);
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