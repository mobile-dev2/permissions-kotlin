package com.example.permissionskotlin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val version = Build.VERSION.SDK_INT

        Log.i("info", "La version de android es: $version")

        if(version > Build.VERSION_CODES.M){
            checkPermissions()
        }
    }

    private fun checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.i("info","Los permisos no han sido otorgados, se llamara a requestPermissions()")
            requestPermissions()
        } else {
            Log.i("info", "Todos los permisos ya han sido otorgados")
        }
    }

    private fun requestPermissions(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA) ||
            ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ||
            ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS) ||
            ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
            //El usuario ya ha rechazado el permiso anteriormente, debemos informarle que vaya a ajustes.

            Log.i("info", "El usuario ya ha rechazado el permiso anteriormente")

        } else {
            Log.i("info", "El usuario nunca ha aceptado ni rechazado el permiso")
        }

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS),
            PERMISSIONS_REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if(grantResults.isNotEmpty()){

                    Log.i("info", "${grantResults.size}")

                    for((index, value) in grantResults.withIndex()){

                        val permission = permissions[index]

                        if(value == PackageManager.PERMISSION_GRANTED){

                        }else{
                            val showRationale = shouldShowRequestPermissionRationale( permission )
                            if(!showRationale){

                                Log.i("info", "it : ")
                                Log.i("info", "checked()")
                            }else{
                                Log.i("info", "it : ")
                                Log.i("info", "not checked()")
                            }
                        }
                    }

                    /*grantResults.forEach {

                        Log.i("info", "it : $it")
                        if ((it == PackageManager.PERMISSION_GRANTED)) {
                            //El usuario ha aceptado el permiso, no tiene porqué darle de nuevo al botón, podemos lanzar la funcionalidad desde aquí.
                            Log.i("info", "se acepto el permiso")
                        } else {
                            //El usuario ha rechazado el permiso, podemos desactivar la funcionalidad o mostrar una vista/diálogo.
                            Log.i("info", "se nego el permiso")
                        }
                    }*/
                }
                //return
            }
            else -> {
                // Este else lo dejamos por si sale un permiso que no teníamos controlado.
            }
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
