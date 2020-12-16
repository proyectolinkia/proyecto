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

public class ActivityMostrarOfertas extends AppCompatActivity {
    String nombrePV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ofertas);
        Bundle parametros = this.getIntent().getExtras();
        nombrePV = parametros.getString("nombrePV");
        downloadJSON_rv("http://35.205.20.239/sql.php?sentenciasql=Select%20idProducto,nombreProducto,precioProducto%20FROM%20Productos%20where%20idPuntoVentafk%20in%20(Select%20idPuntoVenta%20From%20PuntoVenta%20where%20nombrePuntoVenta=%27"+nombrePV+"%27)");
    }
    private void downloadJSON_rv(final String urlWebService) {

        class DownloadJSON_rv extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                try {
                    loadIntoRV(s);
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
        DownloadJSON_rv getJSON = new DownloadJSON_rv();
        getJSON.execute();
    }

    private void loadIntoRV(String json) throws JSONException {
        //  RecyclerView recyclerView;
        //   RecyclerView.Adapter mAdapter;
        //   RecyclerView.LayoutManager layoutManager;
        JSONArray jsonArray = new JSONArray(json);
        String[] puntoVenta = new String[jsonArray.length()];
        ListView listview;
        listview = (ListView) findViewById(R.id.rvOfertas);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            puntoVenta[i] = obj.getString("idProducto")+obj.getString("nombreProducto")+""+obj.getString("precioProducto")+"€";
        }
        // recyclerView = (RecyclerView) findViewById(R.id.rvOfertas);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //   recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // layoutManager = new LinearLayoutManager(this);
        //   recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        //   mAdapter = new MyAdapter(puntoVenta);
        //  recyclerView.setAdapter(mAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  puntoVenta);


        listview.setAdapter(adapter);
    }
}