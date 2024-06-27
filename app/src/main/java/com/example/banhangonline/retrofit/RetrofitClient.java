package com.example.banhangonline.retrofit;

import com.example.banhangonline.model.User;
import com.example.banhangonline.model.Usermodel;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RetrofitClient {
    private static Retrofit instance;
    public static Retrofit getInstance(String baseUrl){
        if (instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }
    public interface ApiBanHang {
        @POST("/dangky")
        Call<Usermodel> dangKy(@Body User user);
    }

}