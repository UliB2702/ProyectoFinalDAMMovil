package com.example.placegiver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class EditarPerfilActivity extends AppCompatActivity {

    Button btnEnviar;
    TextView tvError, tvUsuario;
    Toolbar tb;
    ActionBar ab;
    EditText edtDescripcion, edtEmail, edtPassword;
    private SharedPreferences prefs;
    APIRest api = new APIRest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editarCuenta), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnEnviar = findViewById(R.id.btnGuardarCambios);
        edtDescripcion = findViewById(R.id.etDescripcion);
        edtEmail = findViewById(R.id.etEmailEditar);
        edtPassword = findViewById(R.id.etPasswordEditar);
        tvError = findViewById(R.id.tvError3);
        tvUsuario = findViewById(R.id.tvUsuario);
        tb = findViewById(R.id.toolbar);
        prefs = getSharedPreferences("sesion", MODE_PRIVATE);
        setSupportActionBar(tb);
        ab = getSupportActionBar();

        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        edtDescripcion.setText(prefs.getString("descripcion", ""));
        edtEmail.setText(prefs.getString("email", ""));
        tvUsuario.setText(prefs.getString("nombre", ""));
        edtPassword.setText(prefs.getString("password", ""));

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtPassword.getText().toString().trim().isEmpty()){
                    if(!edtEmail.getText().toString().trim().isEmpty()){
                        api.updateUsuario(tvUsuario.getText().toString().trim(),
                                edtPassword.getText().toString().trim(),
                                edtEmail.getText().toString().trim(),
                                edtDescripcion.getText().toString().trim(),
                                (success -> {
                                    if(success){
                                        Log.i("Exito", "Update hecho");
                                        getSharedPreferences("sesion", MODE_PRIVATE)
                                                .edit()
                                                .putString("email", edtEmail.getText().toString())
                                                .putString("descripcion", edtDescripcion.getText().toString().trim())
                                                .putString("password", edtPassword.getText().toString().trim())
                                                .apply();
                                        Intent intent = new Intent(EditarPerfilActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        tvError.setText("Algun dato no es valido, vuelve a intentarlo");
                                    }
                                }));

                    }
                    else{
                        tvError.setText("El email es un campo obligatorio. No puede estar vacio");
                    }
                }
                else{
                    tvError.setText("La contraseña es un campo obligatorio. No puede estar vacio");
                }


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
            return true;
        };
        return super.onOptionsItemSelected(item);

    }
}