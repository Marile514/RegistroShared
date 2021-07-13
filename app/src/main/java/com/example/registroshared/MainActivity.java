package com.example.registroshared;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText user, password;
    private CheckBox boxClaveGuardar;
    private Button btnAccess, btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inits();
        btnEvents();
        leerArchivoShared();
    }

    private void inits(){
        user = (TextInputEditText) findViewById(R.id.tilUsuario);
        password = (TextInputEditText) findViewById(R.id.tilPass);
        boxClaveGuardar = (CheckBox) findViewById(R.id.boxClave);
        btnAccess = (Button) findViewById(R.id.btnLogin);
        btnExit = (Button) findViewById(R.id.btnCancel);
        crearArchivoShared();
    }

    public boolean validarUsuario(TextInputEditText user){
        if(!user.getText().toString().isEmpty()){
            return true;
        }else{
            user.setError("No ingresó el usuario");
            return false;
        }
    }

    public boolean validarPassword(TextInputEditText password){
        if(!password.getText().toString().isEmpty()){
            return true;
        }else{
            password.setError("No ingresó la contraseña");
            return false;
        }
    }

    private void crearArchivoShared(){
        SharedPreferences sdps = getSharedPreferences("login_usuarios", Context.MODE_PRIVATE);
        String usuario = sdps.getString("Nombre del usuario", "");
        String pass = sdps.getString("Contraseña", "");
        boolean checkearClave = sdps.getBoolean("Recordar clave", false);
        user.setText(usuario);
        password.setText(pass);
        boxClaveGuardar.setChecked(checkearClave);

        if(boxClaveGuardar.isChecked()){
            SharedPreferences.Editor editarLogin = sdps.edit();
            editarLogin.putString("Nombre del usuario", user.getText().toString());
            editarLogin.putString("Contraseña", password.getText().toString());
            editarLogin.putBoolean("Recordar clave", boxClaveGuardar.isChecked());
            editarLogin.commit();
        }
    }

    private void leerArchivoShared(){
        SharedPreferences sdps = getSharedPreferences("login_usuarios", Context.MODE_PRIVATE);
        String restoredText = sdps.getString("text", null);

        if(restoredText != null){
            String nombreUsuario = sdps.getString("nombre del usuario", "");
            String pass = sdps.getString("Contraseña", "");
            user.setText(nombreUsuario);
            password.setText(pass);
        }
    }

    private void btnEvents(){
        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUsuario(user);
                validarPassword(password);
                if(validarUsuario(user) && validarPassword(password)){
                    Toast.makeText(MainActivity.this, "Usuario " + user.getText().toString() + " concebido", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    Intent users = new Intent(MainActivity.this, usuario_registrar.class);
                    startActivity(users);
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void limpiarCampos(){
        user.setText("");
        password.setText("");
    }
}