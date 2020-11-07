package com.example.pruebamaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapsActivity extends /*AppCompatActivity,*/ FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMapLongClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng marcador1 = new LatLng(41.38044,2.17069);
    private LatLng marcador2 = new LatLng(41.38054,2.17080);
    private LatLng marcador3 = new LatLng(41.38042,2.17090);


    public ArrayList<LatLng> ubicasMuestra =new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //   Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ubicasMuestra.add(marcador1);
        ubicasMuestra.add(marcador2);
        ubicasMuestra.add(marcador3);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng marcadorInicioEjemplo = new LatLng(41.38, 2.16);
        mMap.addMarker(new MarkerOptions().position(marcadorInicioEjemplo).title("Soy un marcador de Ejemplo predefinido."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marcadorInicioEjemplo));
        mMap.setMinZoomPreference(15);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMapClickListener(this::onMapLongClick);
        //LatLng miUbica =new LatLng();
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbica));

        for (int i = 0; i<ubicasMuestra.size(); i++){
        mMap.addMarker(new MarkerOptions().position(ubicasMuestra.get(i)).title("Soy un marcador cargado de un array"));
        }

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) { //SIMPLEMENTE DA UN MENSAJE AL CLICAR UBICA
        Toast.makeText(this, "Ubicacion actual:\n" + location, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Dirigiendo a su ubicación", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (El foco dirige al usuario a su actual ubicación).
        return false;
    }

    public void nuevoMarcador(){


    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        LatLng guardaUbica =latLng;
        Toast.makeText(this, "Ubicacion clickada:\n" + guardaUbica, Toast.LENGTH_LONG)
                .show();

    }
}