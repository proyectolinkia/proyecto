package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    // DECLARO VARIABLES DE CONEXION
    Connection con;
    EditText usu, psw;
    String un,pass,db,ip;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // AQUI DECLARO IP NOMBRE USUARIO Y PSW DE LA BBDD
        ip = "35.205.20.239";
        db = "appmibarrio";
        un = "root";
        pass = "11Azse55@";
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void clickRegistro(View view){
        Intent intent = new Intent(this, FormularioRegistro.class);
startActivity(intent);
    }

    public void onClickContinuar(View view) {
        usu =(EditText) findViewById(R.id.campoUsuario); //ojo
        psw= (EditText)findViewById(R.id.campoPsw);// OJO

        CheckLogin checkLogin = new CheckLogin();// EJECUTO LA ASSYNCTASK
        checkLogin.execute("");

    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean exito = false;

        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(exito)
            {
                Toast.makeText(MainActivity.this , "Login Successfull" , Toast.LENGTH_LONG).show();
                //finish();
            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String usernam = usu.getText().toString();
            String passwordd = psw.getText().toString();
            if(usernam.trim().equals("")|| passwordd.trim().equals(""))
                z = "Por favor escribe tu usuario y contraseña";
            else
            {
                try
                {
                    con = connectionclass(un, pass, db, ip);        // Connect to database
                    if (con == null)
                    {
                        z = "Comprueba tu acceso a internet!";
                    }
                    else
                    {
                        // AQUI VIENE EL QUERY!!!
                        String query = "select * from appmibarrio.empresas where emailEmpresa= '" + usernam.toString() + "' and pswrd = '"+ passwordd.toString() +"'  ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            z = "Login correcto";
                            exito=true;
                            con.close();
                        }
                        else
                        {
                            z = "Credenciales invalidas!";
                            exito = false;
                        }
                    }
                }
                catch (Exception ex)
                {
                    exito = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + "/" + database + ";user=" + user+";password="+password; //"jdbc:jtds:sqlserver://" + server +"/"+ database + ";user=" + user+ ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);                                                                                    //jdbc:jtds:<server_type>://<server>[:<port>][/<database>][;<property>=<value>[;...]]
            if(connection != null){
                Log.e("Capullin si se conecta", "aiaiai");
            }
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

}