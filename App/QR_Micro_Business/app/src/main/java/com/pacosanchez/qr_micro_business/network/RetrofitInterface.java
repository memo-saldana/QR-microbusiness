package com.pacosanchez.qr_micro_business.network;

import com.pacosanchez.qr_micro_business.model.Response;
import com.pacosanchez.qr_micro_business.model.Business;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {

    @POST("users")
    Observable<Response> register(@Body Business user);

    @POST("authenticate")
    Observable<Response> login();

    @GET("users/{email}")
    Observable<Business> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body Business user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body Business user);
}