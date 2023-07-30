package com.example.movilessoftware2023a

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class GGoogleMaps : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()
        val boton = findViewById<Button>(R.id.btn_ir_carolina)
        boton.setOnClickListener{
            irCarolina()
        }
    }
    fun irCarolina(){
        val carolina = LatLng(-0.1825684318486696,
        -78.48447277600916)
        val zoom = 17f
        moverConCamaraZoom(carolina, zoom)
    }

    fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            with(googleMap) { //googleMap != null
                mapa = googleMap
                establecerConfiguracionMapa()
                marcadorQuicentro()
                anadirPolilinea()
                anadirPoligono()
                escucharListeners()
            }
        }
    }

    fun escucharListeners(){
        mapa.setOnPolygonClickListener {
            Log.i("mapa", "setPolygonclickListener ${it}")
            it.tag
        }
        mapa.setOnPolylineClickListener {
            Log.i("mapa", "setPolylinelineckListener ${it}")
            it.tag
        }
        mapa.setOnMarkerClickListener {
            Log.i("mapa", "setMarkerclickListener ${it}")
            it.tag
            return@setOnMarkerClickListener true
        }
        mapa.setOnCameraMoveListener {
            Log.i("mapa", "setCameraMoveListener")
        }
        mapa.setOnCameraMoveStartedListener {
            Log.i("mapa", "setCameraMoveStartedListener ${it}")
        }
        mapa.setOnCameraIdleListener {
            Log.i("mapa", "setCameraIdleListener")
        }

    }




    fun anadirPolilinea(){
        with(mapa){
            val poliLineaUno = mapa
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng( -0.1759187040647396,
                            -78.48506472421384),
                        LatLng(-0.17632468492901104,
                        -78.48265589308046),
                        LatLng(-0.17746143130181483,
                        -78.4770533307815)
                        )
                )
            poliLineaUno.tag = "Linea-1"
        }
    }
    fun anadirPoligono(){
        with(mapa){
            val poligonoUno = mapa
                .addPolygon(
                PolygonOptions()
                    .clickable(true)
                    .add(
                        LatLng(-0.1771546902239471,
                            -78.48344987495214),
                        LatLng(-0.17968981486125768,
                            -78.48269198043828),
                        LatLng(-0.17710958124147777,
                            -78.48142892291516)
                    )
            )
            poligonoUno.fillColor = -0xc771c4
            poligonoUno.tag = "poligono-2"
        }
    }
    fun marcadorQuicentro(){
        val zoom = 17f
        val quicentro = LatLng(
            -0.17556708490271092, -78.48014901143776
        )
        val titulo = "Quicentro"
        val markQuicentro = anadirMarcador(quicentro, titulo)
        markQuicentro.tag = titulo
        moverConCamaraZoom(quicentro)
    }
    fun anadirMarcador(latLng: LatLng, title: String): Marker{
        return mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )!!
    }
    fun moverConCamaraZoom(latLng: LatLng, zoom: Float = 10f){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa) {
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienesPermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienesPermisos) {
                mapa.isMyLocationEnabled = true //tenemos permisos
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCuarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                //permiso que van a chekear
                nombrePermisoFine
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos = true
        }else{
            ActivityCompat.requestPermissions(
                this, //contexto
                arrayOf( // arreglo Permisos
                    nombrePermisoFine, nombrePermisoCuarse
                ),
                1 // Codigo de peticion de permisos
            )
        }
    }
}