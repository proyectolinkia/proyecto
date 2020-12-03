package com.example.proyectodam.ClasesMapa;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;

import com.example.proyectodam.ActivityMapa;




   /*
    String direccion;
    LatLng ubica;
    ArrayList<LatLng> ubicaciones = new ArrayList<>();
    List<Address> localizaciones;

    public GetUbicacionFromDireccion() {
    }

    public GetUbicacionFromDireccion(ArrayList<String> direcciones, Context context) {
        Geocoder coder = new Geocoder(context, Locale.getDefault());
        for (int i = 0; i < direcciones.size(); i++) {
            try {

                this.localizaciones = coder.getFromLocationName(direcciones.get(0), 1);
                if (this.localizaciones == null) {
                }
                ubica = new LatLng(this.localizaciones.get(0).getLatitude(), this.localizaciones.get(0).getLongitude());
                this.ubicaciones.add(ubica);

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }

    }

    public ArrayList<LatLng> getLocationFromAddress(Context context, ArrayList<String> direcciones) {

        Geocoder coder = new Geocoder(context, Locale.getDefault());
        List<Address> address = new ArrayList<>();
        ArrayList<LatLng> latitudes = new ArrayList<>();





            for (int i = 0; i < direcciones.size(); i++) {
                try {

                    address = coder.getFromLocationName(direcciones.get(i), 1);
                    if (address == null) {
                        return null;
                    }
                    Address location = address.get(i);
                    LatLng p1 = new LatLng(location.getLatitude(), location.getLongitude());
                    latitudes.add(p1);

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }

            return latitudes;
        }

        public String getDireccion () {
            return direccion;
        }

        public void setDireccion (String direccion){
            this.direccion = direccion;
        }

        public ArrayList<LatLng> getUbicaciones () {
            return ubicaciones;
        }

        public void setUbicaciones (ArrayList < LatLng > ubicaciones) {
            this.ubicaciones = ubicaciones;
        }

        public List<Address> getLocalizaciones () {
            return localizaciones;
        }

        public void setLocalizaciones (ArrayList < Address > localizaciones) {
            this.localizaciones = localizaciones;
        }
    }
*/