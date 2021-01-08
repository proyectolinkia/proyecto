package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class ActivityRegistroEmpresa extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empresa);

    }
    public void onClickGuardarEm(View view) {
     intent = new Intent(this, ActivityMain.class);
        //   usu = (EditText) findViewById(R.id.campoUsuario); //ojo
        //psw = (EditText) findViewById(R.id.campoPsw);// OJO
        String sha1;
        EditText nombreEmpresa=(EditText) findViewById(R.id.campoNombreEm);
        EditText calleEmpresa=(EditText) findViewById(R.id.campoCalleEm);
        EditText numeroEmpresa=(EditText) findViewById(R.id.campoNumCalleEm);
        EditText ciudadEmpresa=(EditText) findViewById(R.id.CiudadEm);
        EditText cpEmpresa=(EditText) findViewById(R.id.cpEm);
        EditText provinciaEmpresa=(EditText) findViewById(R.id.provinciaEm);
        EditText telEmpresa=(EditText) findViewById(R.id.telefonoEm);
        EditText emailEmpresa=(EditText) findViewById(R.id.emailEm);
        EditText passEmpresa=(EditText) findViewById(R.id.passEm);


        String snombreEm=nombreEmpresa.getText().toString();
        String scalleEm=calleEmpresa.getText().toString();
        String snumeroEm=numeroEmpresa.getText().toString();
        String sciudadEm=ciudadEmpresa.getText().toString();
        String scpEm=cpEmpresa.getText().toString();
        String sprovinciaEm=provinciaEmpresa.getText().toString();
        String stelEm=telEmpresa.getText().toString();
        String semailEm=emailEmpresa.getText().toString();
        String spassEm=passEmpresa.getText().toString();

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.reset();
        try {
            digest.update(spassEm.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));

        //  downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20pswrd%20FROM%20Empresas%20where%20nombreEmpresa=%27"+usu+"%27");
        downloadJSON("http://35.205.20.239/sqli_2_em.php?nombre=%27"+snombreEm+"%27&calle=%27"+scalleEm+"%27&numero="+snumeroEm+"&ciudad=%27"+sciudadEm+"%27&provincia=%27"+sprovinciaEm+"%27&cp="+scpEm+"&telefono="+stelEm+"" +
                "&email=%27"+semailEm+"%27&passEmpresa=%27"+sha1+"%27");
        //$nombre,$calle,$numero,$ciudad,$provincia,$cp,$telefono,$email,$idEmpresa
    }
    //   startActivity(intent);


    private void downloadJSON(final String urlWebService) {

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
                                        "Punto de venta creado", Toast.LENGTH_SHORT);

                        toast1.show();
                     //   intent.putExtra("idEmpresa", idEmpresa);
                    //    startActivity(intent);
                    }else if(valor.equals("NOOK")){
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Error al crear el punto de venta.Contacte con el administrador", Toast.LENGTH_SHORT);

                        toast1.show();
                        startActivity(intent);

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

}