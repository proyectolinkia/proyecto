package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PerfilComerciante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_comerciante);
    }

    public void anadePV(View view) {
        Intent intent = new Intent(this, FormularioRegistro.class);
        startActivity(intent);
    }
    public void anadeOferta(View view) {
        Intent intent = new Intent(this, FormularioRegistro.class);
        startActivity(intent);
    }

}