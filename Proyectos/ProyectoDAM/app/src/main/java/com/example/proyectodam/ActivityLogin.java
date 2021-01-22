package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectodam.clasesJSON.DownloadIdPuntosVenta;

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
import java.util.Set;

public class ActivityLogin extends AppCompatActivity {
  EditText usu, psw;
    String valor;
    Set<Integer> idPVUnicos;


    int idEmpresa;
    String nombreEmpresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    //Si se trata de un nuevo registro derivamos a la activity correspondiente
        public void clickRegistro(View view) {
        Intent intent = new Intent(this, ActivityRegistroEmpresa.class);
        startActivity(intent);
    }
   //Al pulsar comprobamos los datos introducidos por el usuario
    public void onClickContinuar(View view) {
        Intent intent = new Intent(this, ActivityPerfilComerciante.class);
        usu = (EditText) findViewById(R.id.campoUsuario);
        psw = (EditText) findViewById(R.id.campoPsw);
        String emailEmpresa = usu.getText().toString();
        //obtenemos los datos de la bbdd con una consulta mediante php
        downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20pswrd,%20idEmpresa,%20nombreEmpresa%20FROM%20Empresas%20where%20emailEmpresa=%27" + emailEmpresa + "%27", intent);
    }
     //Ejecucion de la consulta
    private void downloadJSON(final String urlWebService, final Intent intent) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

           // Una vez realizada la consulta y obtenidos los datos
            //comprobamos password y email. Si correcto vamos al area de
            //empresa si no enviamos un mensaje.
            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                String sha1;
                super.onPostExecute(s);
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject obj = jsonArray.getJSONObject(0);
                    valor = obj.getString("pswrd");
                    idEmpresa = obj.getInt("idEmpresa");
                    nombreEmpresa=obj.getString("nombreEmpresa");
                } catch (JSONException e) {
                }

                //Realizamos una comprobacion contra el hash guardado en BBDD
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
                    intent.putExtra("idEmpresa", idEmpresa);
                    intent.putExtra("nombreEmpresa", nombreEmpresa);
                    startActivity(intent);
                } else {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Los datos no son correctos", Toast.LENGTH_SHORT);

                    toast1.show();
                }
            }
            //Realizamos la conexion y obtenemos un json
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
                    //Controlamos los valores nulos
                    if ((json == null) && sb.toString().equals("[]\n")) {
                        obtenido = "[{\"pswrd\":\"11111\"}]";

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
}