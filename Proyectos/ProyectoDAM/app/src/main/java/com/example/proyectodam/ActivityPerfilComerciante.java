package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//Area de la empresa
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
          //Obtenemos los datos del area de login
        if(parametros !=null){
             idEmpresa = parametros.getInt("idEmpresa");
             nombreEmpresa = parametros.getString("nombreEmpresa");
            txtEmpresa.setText(nombreEmpresa);
        }
        //otenemos los puntos de venta
        downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20idPuntoVenta,nombrePuntoVenta%20FROM%20PuntoVenta%20where%20idEmpresafk="+idEmpresa+"");



    }
  //Para añadir un punto de venta
    public void anadePV(View view) {
        Intent intent = new Intent(this, ActivityFormularioRegistro.class);
        intent.putExtra("id",idEmpresa);
        startActivity(intent);
    }
    //Para añadir ofertas
    public void anadeOferta(View view) {
        Intent intent = new Intent(this, FormularioOfertasActivity.class);
        Spinner sPuntosVenta = (Spinner) findViewById(R.id.spinner);
        String idPV=sPuntosVenta.getSelectedItem().toString();
        intent.putExtra("nombrePV",idPV);
        intent.putExtra("idEmpresa",idEmpresa);
        startActivity(intent);
    }

    //Para ver las ofertas
    public void verOfertas(View view) {

        Intent intent = new Intent(this, ActivityMostrarOfertas.class);
        Spinner sPuntosVenta = (Spinner) findViewById(R.id.spinner);
        String idPV=sPuntosVenta.getSelectedItem().toString();
        intent.putExtra("nombrePV",idPV);
        startActivity(intent);
    }
    //Para borrar una oferta
    public void borrarOferta(View view) {
        Spinner sPuntosVenta = (Spinner) findViewById(R.id.spinner);
        String idPV=sPuntosVenta.getSelectedItem().toString();
        Intent intent = new Intent(this, ActiviyBorrarOferta.class);
        intent.putExtra("id",idEmpresa);
        intent.putExtra("NombrePV",idPV);
        startActivity(intent);
    }
    //Para borrar un punto de venta
    public void borrarPuntoVenta(View view) {
        Spinner sPuntosVenta = (Spinner) findViewById(R.id.spinner);
        String idPV=sPuntosVenta.getSelectedItem().toString();
        idPV=idPV.replace(" ","%20");
        downloadJSON_BPV("http://35.205.20.239/sqli_6.php?nombrePuntoventa=%27"+idPV+"%27&idEmpresa="+idEmpresa);
    }

    //Obtenemos los puntos de venta desde la consulta
    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

           //Cargamos los datos obtenidos en un Spinner
            @Override
            protected void onPostExecute(String s) {
                try {
                    loadIntoListView(s);
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
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }
      //Realizamos la carga en el spinner
    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] puntoVenta = new String[jsonArray.length()];
        Spinner sPuntosVenta = (Spinner) findViewById(R.id.spinner);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            puntoVenta[i] = obj.getString("nombrePuntoVenta");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, puntoVenta);
        sPuntosVenta.setAdapter(arrayAdapter);

    }



//    /*************************************************/
    private void  downloadJSON_BPV(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


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
                                        "Punto de Venta Borrado", Toast.LENGTH_SHORT);
                         toast1.show();
                        downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20idPuntoVenta,nombrePuntoVenta%20FROM%20PuntoVenta%20where%20idEmpresafk="+idEmpresa+"");

                    }else if(valor.equals("NOOK")){
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Error al borrar el punto de venta.Contacte con el administrador", Toast.LENGTH_SHORT);

                        toast1.show();

                    }
                    // String  idEmpresa = obj.getString("idEmpresa");
                } catch (JSONException e) {
                    // e.printStackTrace();
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
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }
    /*********************************************/

}