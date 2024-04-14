package com.example.bmr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)
    }

    fun back(v:View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}