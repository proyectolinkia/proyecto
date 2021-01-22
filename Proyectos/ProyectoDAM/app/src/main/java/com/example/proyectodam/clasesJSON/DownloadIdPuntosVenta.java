package com.example.proyectodam.clasesJSON;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.proyectodam.R;

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
//Clase con los metodos necesarios para obtener los id de puntos de venta guardados en BBDD
public class DownloadIdPuntosVenta {

    ArrayList<Integer> idPV = new ArrayList<>();

    Set<Integer> idPVUnicos;


    public DownloadIdPuntosVenta() throws IOException {
    }

    public DownloadIdPuntosVenta(String url) throws IOException, JSONException {
        downloadJSON(url);

    }
    //Ejecucion de la consulta
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
                loadIntoIdUnicos(s);
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



    private void loadIntoIdUnicos(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            idPV.add(obj.getInt("idPuntoVentafk"));
        }
        idPVUnicos = new HashSet<Integer>(idPV);
    }

    public Set<Integer> getIdPVUnicos() {
        return idPVUnicos;
    }
}



