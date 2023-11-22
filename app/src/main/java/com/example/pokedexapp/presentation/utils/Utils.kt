package com.example.pokedexapp.presentation.utils

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.pokedexapp.R

fun ImageView.downloadUrl(url: String?){
    val options = RequestOptions()
        .placeholder(CircularProgressDrawable(context))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.baseline_error_24)


    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}