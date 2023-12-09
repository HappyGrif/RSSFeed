package com.example.androidrssreaderjava.interfaces;



import com.example.androidrssreaderjava.MainActivity;
import com.example.androidrssreaderjava.model.Rss;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IApiService {
    /*@GET("rss_all.xml")*/
    @GET("rss/")

    Observable<Rss> getRSS();
}
