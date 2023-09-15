package com.example.autoassist.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.autoassist.R

class InicioActivity : AppCompatActivity() {
    private lateinit var imagenVehiculo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        initComponent()

        loadImage()
    }

    private fun initComponent() {
        imagenVehiculo = findViewById(R.id.imagenVehiculoInicio)
    }

    private fun loadImage() {
        val imagen =
            "https://images.patiotuerca.com/thumbs/w/260x153xC/amz_ptf_ecuador/1182023/1765924/o_o/pt_1765924_12152858.jpg"
        Glide.with(this).load(imagen).into(imagenVehiculo)
    }
}