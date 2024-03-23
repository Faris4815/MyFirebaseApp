package com.example.myfirebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChooseUserActivity : AppCompatActivity() {

    var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)

        recyclerView = findViewById(R.id.recyclerView_ID)
        recyclerView?.layoutManager = LinearLayoutManager(this)
    }



}