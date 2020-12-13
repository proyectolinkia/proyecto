package com.example.proyectodam.clasesJSON;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodam.ActivityFormularioRegistro;
import com.example.proyectodam.ActivityMapa;
import com.example.proyectodam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DownloadDirecciones extends AppCompatActivity implements Serializable {
    ArrayList<String> direcciones = new ArrayList<>();
    ArrayList<String> puntosVenta = new ArrayList<>();
    String puntoVenta;
    String direccion;
    JSONArray jsonArray;

    public DownloadDirecciones() {
    }

    public void downloadJSON(final String urlWebService) {
        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                try {
                    loadIntoDirecciones(s);
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


    private void loadIntoDirecciones(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject obj = jsonArray.getJSONObject(0);
        direccion = obj.getString("callePuntoVenta") + " " + obj.getString("calleNumeroPuntoVenta") + ", " + obj.getString("ciudadPuntoVenta");
        direcciones.add(direccion);
        puntoVenta=obj.getString("nombrePuntoVenta");
        puntosVenta.add(puntoVenta);


    }

    public ArrayList<String> getDirecciones() {
        return direcciones;
    }

    public ArrayList<String> getPuntosVenta() {
        return puntosVenta;
    }
}
