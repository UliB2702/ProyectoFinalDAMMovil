package com.example.placegiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroActivity extends AppCompatActivity {

    Toolbar tb;
    Button btnIniciarSesion, btnCrearCuenta;
    EditText etEmail, etNombre, etPassword, etConfirmPassword;
    TextView tvError;
    APIRest api = new APIRest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tb = findViewById(R.id.toolbar3);
        tb.setTitle("");
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        btnIniciarSesion = findViewById(R.id.btnInicioSesion2);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        tvError = findViewById(R.id.tvError2);
        etNombre = findViewById(R.id.txtNombreUsuario);
        etEmail = findViewById(R.id.txtEmail2);
        etPassword = findViewById(R.id.txtPassword2);
        etConfirmPassword = findViewById(R.id.txtConfirmPassword);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroActivity.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNombre.getText().toString().isEmpty() && !etEmail.getText().toString().isEmpty() &&
                !etPassword.getText().toString().isEmpty() && !etConfirmPassword.getText().toString().isEmpty()){


                    if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){

                        api.crearUsuario(etNombre.getText().toString(), etPassword.getText().toString(), etEmail.getText().toString(),success-> {
                            if(success){
                                getSharedPreferences("sesion", MODE_PRIVATE)
                                        .edit()
                                        .putBoolean("login", true)
                                        .putString("nombre", etNombre.getText().toString())
                                        .putString("email",  etEmail.getText().toString())
                                        .putString("descripcion", "")
                                        .putString("password", etPassword.getText().toString())
                                        .apply();
                                Intent i = new Intent(RegistroActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();

                            }
                        });
                        tvError.setText("Algunos datos no son correctos");
                    }
                    else{
                        tvError.setText("La contraseña debe ser igual en ambos campos");
                    }
                }
                else{
                    tvError.setText("Tienen que completarse todos los campos para registrarse");
                }
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