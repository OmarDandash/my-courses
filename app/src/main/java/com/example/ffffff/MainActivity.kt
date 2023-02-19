package com.example.ffffff


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.EditText
import android.widget.ListView
import okhttp3.OkHttpClient
import android.widget.Button
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var moodleApi: MoodleApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tokenInput = findViewById<EditText>(R.id.tokenInput)
        val urlInput = findViewById<EditText>(R.id.urlInput)
        val coursesList = findViewById<ListView>(R.id.coursesList)

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenInput.text.toString()))
            .build()

        val url = urlInput.text.toString().trim()
        if (url.isNotEmpty()) {
            moodleApi = Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoodleApi::class.java)
        } else {
            // toast
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {

            lifecycleScope.launch {
                try {
                    val tokenResponse = moodleApi.getToken(tokenInput.text.toString())

                    val courseResponse = moodleApi.getCourses(tokenResponse.token, userId = 0)

                    val courses = courseResponse.courses.map { it.fullname }
                    coursesList.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, courses)
                } catch (e: Exception) {
                    Log.e(TAG, "Error retrieving courses", e)
                }


            }
        }
    }
}