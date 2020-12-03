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

public class DownloadDirecciones extends AppCompatActivity implements Serializable{
    ArrayList<String> direcciones = new ArrayList<>();
    Set<Integer> idUnicos = new HashSet<>();
    String direccion;
    JSONArray jsonArray;

    public DownloadDirecciones() {
    }

    public DownloadDirecciones(Set<Integer> idUnicos) throws IOException, JSONException {
        Iterator itera = idUnicos.iterator();
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


    }

    public ArrayList<String> getDirecciones() {
        return direcciones;
    }
}
/*
    public void downloadDirecciones(final String urlWebService) {
        //ArrayList<String> direcciones = new ArrayList<>();
        class DownloadJSON extends AsyncTask<Void, Void, String> {
            // String direccion= new String();

            @Override
            protected void onPostExecute(String s){

                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject obj = jsonArray.getJSONObject(0);
                    direccion = obj.getString("callePuntoVenta") + " " + obj.getString("calleNumeroPuntoVenta") + ", " + obj.getString("ciudadPuntoVenta");
                    direcciones.add(direccion);
                } catch (JSONException e) {
                    // e.printStackTrace();
                }}

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }



            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String obtenido = null;
                    String json = " ";
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    if ((json == null) && sb.toString().equals("[]\n")) {
                        obtenido = "";
                    } else {
                        obtenido = sb.toString().trim();
                    }
                    JSONArray jsonArray = null;

                    ;
                    return obtenido;
                } catch (Exception e) {
                    return null;
                }
            }

        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    public ArrayList<String> getDirecciones() {
        return direcciones;
    }



}

        /*       this.idUnicos = idUnicos;
        Iterator itera = idUnicos.iterator();
        while (itera.hasNext()) {
            downloadDirecciones("http://35.205.20.239/sql.php?sentenciasql=Select%20callePuntoVenta,%20calleNumeroPuntoVenta,%20ciudadPuntoVenta%20FROM%20PuntoVenta%20where%20idPuntoVenta=%27" + itera.next() + "%27");
            this.direcciones.add(direccion);
        }
    }

    public ArrayList<String> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(ArrayList<String> direcciones) {
        this.direcciones = direcciones;
    }

    public Set<Integer> getIdUnicos() {
        return idUnicos;
    }

    public void setIdUnicos(Set<Integer> idUnicos) {
        this.idUnicos = idUnicos;
    }

    public void downloadDirecciones(final String urlWebService) throws IOException, JSONException {
        downloadJSON = new DownloadJSON(urlWebService);
        jsonArray = downloadJSON.getJsonArray();
        try {
            JSONObject obj = jsonArray.getJSONObject(0);
            direccion = obj.getString("callePuntoVenta") + " " + obj.getString("calleNumeroPuntoVenta") + ", " + obj.getString("ciudadPuntoVenta");
        }catch(JSONException e){}

    }
}*/
    /*

    public void downloadDirecciones(final String urlWebService) {
        //ArrayList<String> direcciones = new ArrayList<>();
        class DownloadJSON extends AsyncTask<Void, Void, String> {
           // String direccion= new String();

            @Override
            protected void onPostExecute(String s){

                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject obj = jsonArray.getJSONObject(0);
                    direccion = obj.getString("callePuntoVenta") + " " + obj.getString("calleNumeroPuntoVenta") + ", " + obj.getString("ciudadPuntoVenta");
                } catch (JSONException e) {
                    // e.printStackTrace();
                }}

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }



            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String obtenido = null;
                    String json = " ";
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    if ((json == null) && sb.toString().equals("[]\n")) {
                        obtenido = "";
                    } else {
                        obtenido = sb.toString().trim();
                    }
                    JSONArray jsonArray = null;

                    ;
                    return obtenido;
                } catch (Exception e) {
                    return null;
                }
            }

        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

}
*/