package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityPerfilComerciante extends AppCompatActivity {

    int idEmpresa;
    String nombreEmpresa;
  //  final TextView txtEmpresa = findViewById(R.id.txtEmpresa);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_comerciante);
        final TextView txtEmpresa = findViewById(R.id.txtEmpresa);
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
             idEmpresa = parametros.getInt("idEmpresa");
             nombreEmpresa = parametros.getString("nombreEmpresa");
            txtEmpresa.setText(nombreEmpresa);
        }
    }

    public void anadePV(View view) {
        Intent intent = new Intent(this, ActivityFormularioRegistro.class);
        startActivity(intent);
    }
    public void anadeOferta(View view) {
        Intent intent = new Intent(this, ActivityFormularioRegistro.class);
        startActivity(intent);
    }

}