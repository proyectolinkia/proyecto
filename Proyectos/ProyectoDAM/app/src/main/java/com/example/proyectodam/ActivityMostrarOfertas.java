package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//Mostraremos las ofertas del punto de venta seleccionado
public class ActivityMostrarOfertas extends AppCompatActivity {
    String nombrePV;
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ofertas);
        Bundle parametros = this.getIntent().getExtras();
        //Recibimos el punto de venta
        nombrePV = parametros.getString("nombrePV");
        //Sustituimos los espacios en blanco para poder realizar la consulta con HttpURLConnection
        nombrePV=nombrePV.replace(" ","%20");
        //Obtenemos los datos
        downloadJSON_rv("http://35.205.20.239/sql.php?sentenciasql=Select%20idProducto,nombreProducto,precioProducto%20FROM%20Productos%20where%20idPuntoVentafk%20in%20(Select%20idPuntoVenta%20From%20PuntoVenta%20where%20nombrePuntoVenta=%27"+nombrePV+"%27)");
    }
    private void downloadJSON_rv(final String urlWebService) {

        class DownloadJSON_rv extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

             //Una vez recibidos los datos los mostramos en un listView
            @Override
            protected void onPostExecute(String s) {
                try {
                    loadIntoRV(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            //Obtenemos los datos en un json
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
        DownloadJSON_rv getJSON = new DownloadJSON_rv();
        getJSON.execute();
    }
    //Cargamos los datos en un ListView
    private void loadIntoRV(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);
        String[] puntoVenta = new String[jsonArray.length()];
        ListView listview;
        listview = (ListView) findViewById(R.id.rvOfertas);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            puntoVenta[i] =" Ref: "+ obj.getString("idProducto")+". Nombre:"+obj.getString("nombreProducto")+". Precio: "+obj.getString("precioProducto")+"€";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  puntoVenta);


        listview.setAdapter(adapter);
    }
}