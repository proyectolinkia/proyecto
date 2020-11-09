package com.example.appmibarrio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                try {
//                    Class.forName("com.mysql.jdbc.Driver").newInstance ();
                    // "jdbc:mysql://IP:PUERTO/DB", "USER", "PASSWORD");
//                    // Si estás utilizando el emulador de android y tenes el mysql en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
//                    Connection conn = DriverManager.getConnection("jdbc:mysql://35.205.20.239:3306/appmibarrio", "root", "11Azse55@");
//                    //En el stsql se puede agregar cualquier consulta SQL deseada.
//                    String stsql = "Select version()";
//                    Statement st = conn.createStatement();
//                    ResultSet rs = st.executeQuery(stsql);
//                    rs.next();
//                    System.out.println( rs.getString(1) );
//                    conn.close();
//                } catch (SQLException se) {
//                    System.out.println("oops! No se puede conectar. Error: " + se.toString());
//                } catch (ClassNotFoundException e) {
//                    System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                }

                ConexionClandestina con= new ConexionClandestina();
                 con.execute();
            //    new ConexionClandestina().execute();
          }
        });

    }
}