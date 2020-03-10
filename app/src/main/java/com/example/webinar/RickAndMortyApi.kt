package com.example.webinar

import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET ("character")
    fun getCharacters(): Call<CharacterResponse>
}






//call z