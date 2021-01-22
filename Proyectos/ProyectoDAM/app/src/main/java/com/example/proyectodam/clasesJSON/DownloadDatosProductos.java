package com.example.proyectodam.clasesJSON;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
//Clase con los metodos necesarios para obtener los productos guardados en BBDD
public class DownloadDatosProductos {
    ArrayList<Producto> listaProductos = new ArrayList<>();
    Producto producto;
    String nombreBuilder;
    String descripcionBuilder;
    Double precioBuilder;
    int idPVBuilder;

    public DownloadDatosProductos() throws IOException {
    }


    public class Producto {
        String nombre;
        String descripcion;
        Double precio;
        int idPV;

        public Producto(String nombre, String descripcion, Double precio, int idPV) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.precio = precio;
            this.idPV = idPV;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public Double getPrecio() {
            return precio;
        }

        public int getIdPV() {
            return idPV;
        }
    }

    public void downloadJSON(final String urlWebService) {
        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //Tratamos los datos obtenidos en formato json
            @Override
            protected void onPostExecute(String s) {
                try {
                    loadIntoListaProductos(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
             //Ejecutamos la consulta y obtenemos un json
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }


    private void loadIntoListaProductos(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            nombreBuilder = obj.getString("nombreProducto");
            descripcionBuilder = obj.getString("descripcionProducto");
            precioBuilder = obj.getDouble("precioProducto");
            idPVBuilder = obj.getInt("idPuntoVentafk");
            producto = new Producto(nombreBuilder, descripcionBuilder, precioBuilder, idPVBuilder);
            listaProductos.add(producto);
        }
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }
}


