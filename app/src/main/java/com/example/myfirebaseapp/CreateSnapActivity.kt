package com.example.myfirebaseapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class CreateSnapActivity : AppCompatActivity() {

    var picture_IV : ImageView? = null
    var text_ET : EditText? = null
    var selectPicture_BTN : Button? = null
    var send_BTN : Button? = null
    lateinit var getImage : ActivityResultLauncher<String>

    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        picture_IV = findViewById(R.id.picture_IV)
        text_ET = findViewById(R.id.text_ET)
        selectPicture_BTN = findViewById(R.id.selectPicture_BTN)
        send_BTN = findViewById(R.id.send_BTN)


        getImage = registerForActivityResult(
            ActivityResultContracts.GetContent() , ActivityResultCallback  {
                picture_IV?.setImageURI(it)
        })

        selectPicture_BTN?.setOnClickListener{

            if(checkReadStoragePermission()){
                getImage.launch("image/*")
            } else {
                request_READ_EXTERNAL_STORAGE_PERMISSON()
            }


        }


        }

    private fun checkReadStoragePermission() : Boolean {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        return if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("PermissionCheck", "Permission to read Storage denied")
            Log.i("READ_EXTERNAL_STORAGE:", permission.toString())
            false
        } else {
            Log.i("PermissionCheck", "Permission to read Storage granted")
            Log.i("READ_EXTERNAL_STORAGE:", permission.toString())
            true
        }
    }

    fun request_READ_EXTERNAL_STORAGE_PERMISSON(){

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_EXTERNAL_STORAGE_REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getImage.launch("image/*")
        }
    }

}



