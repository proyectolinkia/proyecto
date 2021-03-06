package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityFormularioRegistro extends AppCompatActivity {
    int idEmpresa;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_registro);
        Bundle parametros = this.getIntent().getExtras();
        //Obtenemos el identificador de la empresa para utilizarlo en la consulta
        idEmpresa=parametros.getInt("id");

    }
    //Al guardar obtenemos los datos desde el formulario y ejecutamos el guardado
    public void onClickGuardar(View view) {
       intent = new Intent(this, ActivityPerfilComerciante.class);


        EditText nombreEmpresa=(EditText) findViewById(R.id.campoNombrePV);
        EditText calleEmpresa=(EditText) findViewById(R.id.campoCallePV);
        EditText numeroEmpresa=(EditText) findViewById(R.id.campoNumCallePV);
        EditText ciudadEmpresa=(EditText) findViewById(R.id.CiudadPV);
        EditText cpEmpresa=(EditText) findViewById(R.id.cpPV);
        EditText provinciaEmpresa=(EditText) findViewById(R.id.provinciaPV);
        EditText telEmpresa=(EditText) findViewById(R.id.telefonoPV);
        EditText emailEmpresa=(EditText) findViewById(R.id.emailPV);


        String snombreEm=nombreEmpresa.getText().toString();
        String scalleEm=calleEmpresa.getText().toString();
        String snumeroEm=numeroEmpresa.getText().toString();
        String sciudadEm=ciudadEmpresa.getText().toString();
        String scpEm=cpEmpresa.getText().toString();
        String sprovinciaEm=provinciaEmpresa.getText().toString();
        String stelEm=telEmpresa.getText().toString();
        String semailEm=emailEmpresa.getText().toString();

        snombreEm=snombreEm.replace(" ","%20");
        scalleEm=scalleEm.replace(" ","%20");
        sciudadEm=sciudadEm.replace(" ","%20");
        sprovinciaEm=sprovinciaEm.replace(" ","%20");
        if(snombreEm.equals("")|scalleEm.equals("") |snumeroEm.equals("") |sciudadEm.equals("") |scpEm.equals("")| sprovinciaEm.equals("") |stelEm.equals("") |semailEm.equals(""))
        {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Todos los datos son necesarios.Complete el formulario", Toast.LENGTH_SHORT);

            toast1.show();
        }else {
            //Ejecutamos el guardado en la BBDD utilizando un php específico
            downloadJSON("http://35.205.20.239/sqli_2.php?nombre=%27" + snombreEm + "%27&calle=%27" + scalleEm + "%27&numero=" + snumeroEm + "&ciudad=%27" + sciudadEm + "%27&provincia=%27" + sprovinciaEm + "%27&cp=" + scpEm + "&telefono=" + stelEm + "" +
                    "&email=%27" + semailEm + "%27&idEmpresa=" + idEmpresa + "");
        }
    }



    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

           //Comprobamos el resultado del json y en función del resultado
           //enviamos un mensaje de ok o de error
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
                     intent.putExtra("idEmpresa", idEmpresa);
                     startActivity(intent);
                 }else if(valor.equals("NOOK")){
                     Toast toast1 =
                             Toast.makeText(getApplicationContext(),
                                     "Error al crear el punto de venta.Contacte con el administrador", Toast.LENGTH_SHORT);

                     toast1.show();

                 }
                // String  idEmpresa = obj.getString("idEmpresa");
                } catch (JSONException e) {
                    // e.printStackTrace();
                }


            }
            //Ejecutamos el guardado y recibimos la respuesta medidante un json
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