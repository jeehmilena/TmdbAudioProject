package com.example.tmdbaudio.network;

import com.example.tmdbaudio.model.Artista;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("/track-top10.php")
    Observable<Artista> getAllAlbunArtist(
            @Query("s") String artista);
}
