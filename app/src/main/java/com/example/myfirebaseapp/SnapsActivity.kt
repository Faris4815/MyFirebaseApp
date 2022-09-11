package com.example.myfirebaseapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class SnapsActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    val PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)


    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.snaps, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.createSnap -> {
                //Wechsle zur Activity, wo man einen snap erstellen kann
                val intent = Intent(this, CreateSnapActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                mAuth.signOut()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mAuth.signOut()
    }





}