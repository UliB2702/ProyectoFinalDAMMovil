package com.example.placegiver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IniciarSesionActivity extends AppCompatActivity {

    Toolbar tb;
    TextView tvError;
    Button btnInicioSesion, btnRegistro;
    EditText etNombre, etPassword;

    APIRest api = new APIRest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inicio_sesion), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tb = findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();

        tvError = findViewById(R.id.tvError);
        btnInicioSesion = findViewById(R.id.btnInicioSesion);
        btnRegistro = findViewById(R.id.btnRegistro);
        etNombre = findViewById(R.id.txtNombre);
        etPassword = findViewById(R.id.txtPassword);

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etNombre.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
                    api.obtenerDatosUsuario(etNombre.getText().toString(), etPassword.getText().toString(),(success,usuario)-> {
                    if (success) {
                        getSharedPreferences("sesion", MODE_PRIVATE)
                                .edit()
                                .putBoolean("login", true)
                                .putString("nombre", usuario.getNombre())
                                .putString("email", usuario.getEmail())
                                .apply();
                        Intent intent = new Intent(IniciarSesionActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                    tvError.setText("Algunos de los datos es incorrecto. Ingresalos nuevamente");
                }
                else{
                    tvError.setText("Completa los campos para poder seguir");
                }

            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciarSesionActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }

}