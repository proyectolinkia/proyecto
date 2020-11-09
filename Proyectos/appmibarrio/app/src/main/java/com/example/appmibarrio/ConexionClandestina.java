package com.example.appmibarrio;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionClandestina extends AsyncTask<String,Integer,Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        try {
        //    String cadenaConexion = "jdbc:mysql://35.205.20.239;databaseName=appmibarrio;USER=root;PASSWORD=11Azse55@";
            Class.forName( "com.mysql.cj.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://35.205.20.239:3306/appmibarrio", "root", "11Azse55@");
            if (con == null)
            {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                return false;
            }else{

                System.out.println("*************************************************");
            };
        } catch (NoClassDefFoundError e){
            Log.e("Definicion de clase",e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("Clase no encontrada",e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR Conexion:",e.getMessage());
            return false;
        }
        return true;
    }
    @Override
    protected void onPostExecute(Boolean resultado) {
        if(resultado) {
            Log.e("LOG:", "conectado");
        }else {
            Log.e("LOG:", "no conectado");
        }
    }
}
