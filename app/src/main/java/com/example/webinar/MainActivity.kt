package com.example.webinar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//interceptor
//

class MainActivity : AppCompatActivity() {
    private val api by lazy{ createApi()}      //by lazy tzn: inicjalizacja przy pierwszym wywolania kodu z tym obiektem
    private val adapter by lazy { CharacterAdapter()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //asynchroniczne wyslanie zapytania
        initRecycler()
        getCharacters()
    }

    private fun initRecycler() {
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.adapter = adapter

    }

    private fun getCharacters() {
        api.getCharacters().enqueue(object : Callback<CharacterResponse> {
            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Log.d("nie dziala","tomek")
                t.printStackTrace() //show error
            }

            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if (response.isSuccessful) {
                        response.body()?.let {  //let zeby w body nie bylo nulla
                            adapter.setCharacters(it.results)
                        }
                }
            }

        })
    }


    private fun createApi(): RickAndMortyApi {
        val baseUrl = "https://rickandmortyapi.com/api/"

        val interceptor = HttpLoggingInterceptor()              //luka bezpieczenstwa !!!
            .apply { level = HttpLoggingInterceptor.Level.BODY }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())  //converter jsonow
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

        return retrofit.create(RickAndMortyApi::class.java)
    }
}
