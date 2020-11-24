package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
        downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20idPuntoVenta,nombrePuntoVenta%20FROM%20PuntoVenta%20where%20idEmpresafk="+idEmpresa+"");
    }

    public void anadePV(View view) {
        Intent intent = new Intent(this, ActivityFormularioRegistro.class);
        intent.putExtra("id",idEmpresa);
        startActivity(intent);
    }
    public void anadeOferta(View view) {
        Intent intent = new Intent(this, ActivityFormularioRegistro.class);
        startActivity(intent);
    }
    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


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
}