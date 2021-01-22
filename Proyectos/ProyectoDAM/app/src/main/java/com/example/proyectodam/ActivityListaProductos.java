package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
//Utilizamos esta clase para realizar la carga de las ofertas en
//el detalle del mapa
public class ActivityListaProductos extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    ArrayList<String> listaProductos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        mRecyclerView = findViewById(R.id.recyclerView);

        Bundle parametros = this.getIntent().getExtras();


        if (parametros != null) {
            listaProductos = parametros.getStringArrayList("productos");
        }

        // Esta línea mejora el rendimiento, si sabemos que el contenido
        // no va a afectar al tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        String[] Productos = new String[listaProductos.size()];
        int salto = 0;
        int linea = 0;

        for (int i = 0; i < listaProductos.size(); i++) {
            String saltoProducto = "____________________\nOferta número " + ((i/3) + 1) + " :\n\n";

            if (salto == linea) {
                Productos[i] = saltoProducto + listaProductos.get(i);
                salto=salto+3;
            } else {
                Productos[i] = listaProductos.get(i);
            }
        linea=linea+1;
        }

        // Asociamos un adapter (ver más adelante cómo definirlo)
        mAdapter = new MyAdapter(Productos);
        mRecyclerView.setAdapter(mAdapter);
    }
}