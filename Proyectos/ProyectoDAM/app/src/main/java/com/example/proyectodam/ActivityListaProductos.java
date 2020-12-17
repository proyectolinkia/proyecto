package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ActivityListaProductos extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    ArrayList<String> listaProductos= new ArrayList<>();

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

        for (int i =0; i<listaProductos.size(); i++){
            Productos[i]=listaProductos.get(i);
        }

        // Asociamos un adapter (ver más adelante cómo definirlo)
        mAdapter = new MyAdapter(Productos);
        mRecyclerView.setAdapter(mAdapter);
    }
}