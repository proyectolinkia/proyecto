package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActiviyBorrarOferta extends AppCompatActivity {
    int idEmpresa;
    String puntoVenta;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_borrar_oferta);
        intent = new Intent(this, ActivityPerfilComerciante.class);
        Bundle parametros = this.getIntent().getExtras();
        idEmpresa=parametros.getInt("id");
        puntoVenta=parametros.getString("NombrePV");
        puntoVenta=puntoVenta.replace(" ","%20");

        //Rellenamos el spinner con los datos obtenidos en consulta a bbdd
        downloadJSON_sp("http://35.205.20.239/sql.php?sentenciasql=Select%20idProducto,nombreProducto,precioProducto%20FROM%20Productos%20where%20idPuntoVentafk%20in%20(Select%20idPuntoVenta%20From%20PuntoVenta%20where%20nombrePuntoVenta=%27"+puntoVenta+"%27)");
    }

    //Borramos la oferta seleccionada
    public void borrarOfertaF(View view) {
        Spinner sPuntosVentaSel = (Spinner) findViewById(R.id.spinnerBo);
        String tosplit=sPuntosVentaSel.getSelectedItem().toString();
        String[] arrSplit = tosplit.split(";");
        String tosplit2=arrSplit[0];
        String[] arrSplit2 = tosplit2.split("=");
        String nombre=arrSplit2[1];
        //Ejecutamos la consulta en BBDD
        downloadJSON_Bo("http://35.205.20.239/sqli_5.php?idProducto="+nombre+"");
    }

    private void downloadJSON_sp(final String urlWebService) {

        class DownloadJSON_sp extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                try {
                    loadIntoSp(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

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
        DownloadJSON_sp getJSON = new DownloadJSON_sp();
        getJSON.execute();
    }
//Cargamos las ofertas del punto de venta en un spinner
    private void loadIntoSp(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);
        String[] ofertas = new String[jsonArray.length()];
        Spinner sOfertas = (Spinner) findViewById(R.id.spinnerBo);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            ofertas[i] ="idProducto="+ obj.getString("idProducto") +"; nombreProducto="+ obj.getString("nombreProducto") ;
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ofertas);
        sOfertas.setAdapter(arrayAdapter);
    }
    /*******************************************************/

    //Se ejecuta el borrado
    private void  downloadJSON_Bo(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

           //El resultado se recibe como un json
            @Override
            protected void onPostExecute(String s) {

                String  valor;
                String sha1;
                super.onPostExecute(s);
                //   Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    //    jsonArray = new JSONArray(s);
                    JSONObject obj = new JSONObject(s);
                    valor = obj.getString("resultado");
                    if(valor.equals("OK")){

                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Oferta Borrada", Toast.LENGTH_SHORT);

                        toast1.show();
                        intent.putExtra("idEmpresa", idEmpresa);
                        startActivity(intent);
                    }else if(valor.equals("NOOK")){
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Error al borrar la oferta.Contacte con el administrador", Toast.LENGTH_SHORT);

                        toast1.show();

                    }

                } catch (JSONException e) {

                }


            }
            //Ejecutamos la consulta  y recibimos el resultado
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

}