package com.example.proyectodam;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.proyectodam.ClasesMapa.CustomInfoWindowAdapter;
import com.example.proyectodam.clasesJSON.DownloadDatosProductos;
import com.example.proyectodam.clasesJSON.DownloadDirecciones;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ActivityMapa extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback
        {

    private GoogleMap mMap;
    ArrayList<String> direcciones = new ArrayList<>();
    ArrayList<String> puntosVenta = new ArrayList<>();
    public ArrayList<LatLng> ubicaciones = new ArrayList<>();
    DownloadDirecciones downloadDirecciones = new DownloadDirecciones();
    DownloadDatosProductos datosProductos = new DownloadDatosProductos();
    ArrayList<DownloadDatosProductos> downloadDatosProductos = new ArrayList<>();
    ArrayList<Integer> idPVUnicos;
    private List<Address> localizaciones;
    private LatLng ubica;

            public ActivityMapa() throws IOException {
            }


            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle parametros = this.getIntent().getExtras();


        if (parametros != null) {
            idPVUnicos = parametros.getIntegerArrayList("idUnicos");
        }
        for (int i = 0; i < idPVUnicos.size(); i++) {
            downloadDirecciones.downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20nombrePuntoVenta,%20callePuntoVenta,%20calleNumeroPuntoVenta,%20ciudadPuntoVenta%20FROM%20PuntoVenta%20where%20idPuntoVenta=" + idPVUnicos.get(i));

        }
                datosProductos.downloadJSON("http://35.205.20.239/sql.php?sentenciasql=Select%20nombreProducto,%20descripcionProducto,%20precioProducto,%20idPuntoVentafk%20FROM%20Productos");
                downloadDatosProductos.add(datosProductos);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     **/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: CONSIDERAR LLAMAR A
            //    ActivityCompat#requestPermissions
            // AQUI MIRAR TEMA PERMISOS MAS ADELANTE DE MOMENTO ACTIVO A MANO EN EL EMULADOR
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnInfoWindowClickListener(this);


            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        LatLng miUbica = new LatLng(location.getLatitude(), location.getLongitude()); //obtenemos coordenadas de mi ubicación
        mMap.setMinZoomPreference(15); //AQUI ZOOM MINIMO INICIAL
        mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbica)); //centramos camara en mi ubica
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(this)));


    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Dirigiendo a su ubicación", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (El foco dirige al usuario a su actual ubicación, en caso de que se haya perdido por el mapa. es la respuesta click al boton de arriba a la derecha).
        return false;
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, ActivityListaProductos.class);
        String id = marker.getId();
        //intent.putExtra("productos", downloadDatosProductos);
        startActivity(intent);
    }

    public void onClickCargar(View view) {
        direcciones = downloadDirecciones.getDirecciones();
        puntosVenta = downloadDirecciones.getPuntosVenta();
        Geocoder coder = new Geocoder(this, Locale.getDefault());

        for (int i = 0; i < direcciones.size(); i++) {
            try {

                this.localizaciones = coder.getFromLocationName(direcciones.get(i), 1);
                if (this.localizaciones == null) {
                }
                ubica = new LatLng(this.localizaciones.get(0).getLatitude(), this.localizaciones.get(0).getLongitude());
                this.ubicaciones.add(ubica);

            } catch (Exception ex) {

                ex.printStackTrace();
            }

        }


        for (int i = 0; i < ubicaciones.size(); i++) {

            mMap.addMarker(new MarkerOptions().position(ubicaciones.get(i)).title(puntosVenta.get(i)).snippet(direcciones.get(i)));

        }


    }
}
