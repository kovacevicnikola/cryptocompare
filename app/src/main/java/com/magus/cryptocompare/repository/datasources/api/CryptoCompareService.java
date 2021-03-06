package com.magus.cryptocompare.repository.datasources.api;

import com.magus.cryptocompare.repository.datasources.api.schemas.ResponseModel;

import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CryptoCompareService {
    @GET("/data/v2/histoday")
    @OkHttpTimeouts(read = 5, write = 5)
    Call<ResponseModel> getDataByDay(@Query("api_key") String apiKey, @Query("limit") int limit, @Query("fsym") String valueFrom, @Query("tsym") String valueTo);

    @GET("/data/v2/histohour")
    @OkHttpTimeouts(read = 5, write = 5)
    Call<ResponseModel> getDataByHour(@Query("api_key") String apiKey, @Query("limit") int limit, @Query("fsym") String valueFrom, @Query("tsym") String valueTo);

    @GET("/data/v2/histominute")
    @OkHttpTimeouts(read = 5, write = 5)
    Call<ResponseModel> getDataByMinute(@Query("api_key") String apiKey, @Query("limit") int limit, @Query("fsym") String valueFrom, @Query("tsym") String valueTo);

    @GET("/data/all/coinlist")
    @OkHttpTimeouts(read = 20, write = 20)
    Call<ResponseModel> getCryptoCoinList(@Query("summary") boolean summary, @Query("api_key") String apiKey);

    @GET("/data/price")
    Call<LinkedHashMap<String, String>> getCoinExchangeModel(@Query("api_key") String apiKey, @Query("fsym") String valueFrom, @Query("tsyms") String[] valueTo);
}
