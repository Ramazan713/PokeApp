package com.example.pokedexapp

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApp: Application() {
}