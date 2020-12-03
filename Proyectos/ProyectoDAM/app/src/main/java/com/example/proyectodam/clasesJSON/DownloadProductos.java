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

public class DownloadProductos {

    ArrayList<Integer> idPV = new ArrayList<>();
    ArrayList<String> direcciones = new ArrayList<>();
    Set<Integer> idPVUnicos;


    public DownloadProductos() throws IOException {
    }

    public DownloadProductos(String url) throws IOException, JSONException {
        downloadJSON(url);

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
                loadIntoIdUnicos(s);
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



    private void loadIntoIdUnicos(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ArrayList<Integer> idPV = new ArrayList<>();
        //  Spinner i = (Spinner) findViewById(R.id.spinner);
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



    /*
    private void downloadProductos(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                String sha1;
                super.onPostExecute(s);
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        idPV.add(obj.getInt("idPuntoVentafk"));

                    }
                    idUnicos = new HashSet<Integer>(idPV); //esto filtra valores duplicados


                } catch (JSONException e) {
                    // e.printStackTrace();
                }

                //  intent.putExtra("idPV", idPV);

                // startActivity(intent);

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
*/



        /* this.downloadJSON = new DownloadJSON(url);
        String obtenido = this.downloadJSON.getObtenido();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                idPV.add(obj.getInt("idPuntoVentafk"));

            }
        } catch (JSONException e) {
            // e.printStackTrace();
        }

        Set<Integer> idUnicos = new HashSet<Integer>(idPV); //esto filtra valores duplicados
        this.downloadDirecciones = new DownloadDirecciones(idUnicos);
        this.direcciones = downloadDirecciones.getDirecciones();
    }

    public void setDirecciones(ArrayList<String> direcciones) {
        this.direcciones = direcciones;
    }

    public ArrayList<String> getDirecciones() {
        return direcciones;
    }
}

*/