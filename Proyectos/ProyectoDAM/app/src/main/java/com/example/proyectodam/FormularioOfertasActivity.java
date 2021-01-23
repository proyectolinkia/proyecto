package com.example.proyectodam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class FormularioOfertasActivity extends AppCompatActivity {
    int idEmpresa;
    String nombrePV;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, ActivityPerfilComerciante.class);
        setContentView(R.layout.formulario_ofertas);
        Bundle parametros = this.getIntent().getExtras();
        //Obtenemos los datos de la empresa y el punto de venta
        if(parametros !=null){
            idEmpresa = parametros.getInt("idEmpresa");
            nombrePV = parametros.getString("nombrePV");

        }
      //Cargamos las categorías de productos
        downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20nombreCategoriaProducto%20FROM%20CategoriasProductos%20");
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
      //Cargamos los datos obtenidos en el json el spinner
    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] categorias= new String[jsonArray.length()];
        Spinner sCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
           categorias[i] = obj.getString("nombreCategoriaProducto");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        sCategorias.setAdapter(arrayAdapter);

    }

    //Guardamos la oferta en bbdd
    public void onClickGuardarOferta(View view) {

        EditText referencia=(EditText) findViewById(R.id.campoReferencia);
        EditText nombre=(EditText) findViewById(R.id.campoDenominacionProd);
        EditText descripcion=(EditText) findViewById(R.id.campoDescripcionProd);
        EditText precio=(EditText) findViewById(R.id.campoPrecio);
        Spinner sCategorias = (Spinner) findViewById(R.id.spinnerCategorias);

        String referenciaP=referencia.getText().toString();
        String nombreP=nombre.getText().toString();
        String descripcionP=descripcion.getText().toString();
        Double precioP=Double.parseDouble(precio.getText().toString());
        String categoriaP= sCategorias.getSelectedItem().toString();

        nombrePV=nombrePV.replace(" ","%20");
        referenciaP=referenciaP.replace(" ","%20");
        nombreP= nombreP.replace(" ","%20");
        descripcionP=descripcionP.replace(" ","%20");
        //Ejecutamos la consulta en bbdd
        downloadJSONGO("http://35.205.20.239/sqli_4.php?referencia=%27"+referenciaP+"%27&nombre=%27"+nombreP+"%27&descripcion=%27"+descripcionP+"%27&precio="+precioP+"&nombreCategoria=%27"+categoriaP+"%27&" +
                "&nombrePuntoVenta=%27"+nombrePV+"%27&idEmpresa="+idEmpresa+"");

    }


 //Ejecución del guardado en BBDD
    private void downloadJSONGO(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

           //Obtenemos el resultado del guardado en un json
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
                                        "Oferta creada", Toast.LENGTH_SHORT);

                        toast1.show();
                        intent.putExtra("idEmpresa", idEmpresa);
                        startActivity(intent);
                    }else if(valor.equals("NOOK")){
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Error al crear la oferta.Contacte con el administrador", Toast.LENGTH_SHORT);

                        toast1.show();

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