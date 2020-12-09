package com.example.proyectodam.ClasesMapa;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.proyectodam.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;
    String puntoVenta = new String();


    public CustomInfoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.puntoVenta = puntoVenta;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.info_window_layout, null);
        String title = m.getTitle();
        SpannableString titleText = new SpannableString(title);
        titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
        String snipet = m.getSnippet();
        ((TextView) v.findViewById(R.id.info_window_nombre)).setText(titleText);
        ((TextView) v.findViewById(R.id.info_window_direcciones)).setText(snipet);


        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }

    public void setPuntoVenta(String puntoVenta) {
        this.puntoVenta = puntoVenta;
    }
}