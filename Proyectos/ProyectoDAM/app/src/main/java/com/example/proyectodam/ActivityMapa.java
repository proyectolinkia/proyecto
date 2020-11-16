package com.example.proyectodam;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ActivityMapa extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
       // GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
   // private LatLng marcador1 = new LatLng(41.38044,2.17069);
    //private LatLng marcador2 = new LatLng(41.38054,2.17080);
    //private LatLng marcador3 = new LatLng(41.38042,2.17090);


//    public ArrayList<LatLng> ubicasMuestra =new ArrayList<>();



ArrayList<Integer> idPV= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            idPV = parametros.getIntegerArrayList("idPV");
           }

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
        //mMap.setOnMyLocationClickListener(this);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        LatLng miUbica = new LatLng(location.getLatitude(), location.getLongitude()); //obtenemos coordenadas de mi ubicación
       mMap.setMinZoomPreference(15); //AQUI ZOOM MINIMO INICIAL
        mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbica)); //centramos camara en mi ubica
       // for (int i = 0; i<ubicasMuestra.size(); i++){
          //  mMap.addMarker(new MarkerOptions().position(ubicasMuestra.get(i)).title("Soy un marcador cargado de un array"));
        //}
        //la funcion comentada sirve para añadir un arraylist del tipo LatLng a nuestro mapa en forma de marcadores.

    }
    //SIMPLEMENTE DA UN MENSAJE AL CLICAR UBICA la dejo comentada en principio no la vamos a necesitar
    //@Override
    //public void onMyLocationClick(@NonNull Location location) { //SIMPLEMENTE DA UN MENSAJE AL CLICAR UBICA
        //Toast.makeText(this, "Ubicacion actual:\n" + location, Toast.LENGTH_LONG)
           //     .show();
    //}

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Dirigiendo a su ubicación", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (El foco dirige al usuario a su actual ubicación, en caso de que se haya perdido por el mapa. es la respuesta click al boton de arriba a la derecha).
        return false;
    }


}