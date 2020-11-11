package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public class ActivityMain extends AppCompatActivity {

    // DECLARO VARIABLES DE CONEXION
    Connection con;
    EditText usu, psw;
    ProgressBar progressBar;
	String valor;
    //final String urlWebService= "http://35.205.20.239/sql.php?sentenciasql=Select%20nombreEmpresa%20FROM%20Empresas%20where%20nombreEmpresa=%27demoEmpresa%27";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void clickRegistro(View view) {
        Intent intent = new Intent(this, ActivityFormularioRegistro.class);
        startActivity(intent);
    }

    public void onClickMapa(View view) {
        Intent intent = new Intent(this, ActivityMapa.class);
        startActivity(intent);
    }

    public void onClickContinuar(View view) {
        Intent intent = new Intent(this, ActivityPerfilComerciante.class);
        usu = (EditText) findViewById(R.id.campoUsuario); //ojo
        psw = (EditText) findViewById(R.id.campoPsw);// OJO
       String nombreEmpresa=usu.getText().toString();
      //  downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20pswrd%20FROM%20Empresas%20where%20nombreEmpresa=%27"+usu+"%27");
     downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20pswrd%20FROM%20Empresas%20where%20nombreEmpresa=%27"+nombreEmpresa+"%27",intent);
     //   startActivity(intent);

    }

    private void downloadJSON(final String urlWebService,Intent intent) {

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
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject obj = jsonArray.getJSONObject(0);
                    valor = obj.getString("pswrd");
                } catch (JSONException e) {
                    // e.printStackTrace();
                }

                //   String sha1 = jsonArray.;
                //  String sha2 = "";

                // With the java libraries

                MessageDigest digest = null;
                try {
                    digest = MessageDigest.getInstance("SHA-1");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                digest.reset();
                try {
                    digest.update(psw.getText().toString().getBytes("utf8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sha1 = String.format("%040x", new BigInteger(1, digest.digest()));

                if (sha1.equals(valor)) {
                    startActivity(intent);
                } else {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Los datos no son corrrectos", Toast.LENGTH_SHORT);

                    toast1.show();
                }
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
                    if((json == null)&&sb.toString().equals("[]\n")) {
                        obtenido = "[{\"pswrd\":\"11111\"}]";

                    }else{obtenido=sb.toString().trim();};
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