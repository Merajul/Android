package com.nessbit.vojonbari.service.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static Retrofit retrofit;
   
    //private static final String BASE_URL = "http://192.168.1.2/rannaghor/";
    //private static final String BASE_URL = "http://192.168.0.103/vojonbari/";

    public static Retrofit newNetworkClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
