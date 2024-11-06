package com.example.movieonlineapp.data.source.remote;

import com.example.movieonlineapp.DataLocalManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.100.118:8080/";
//    private static final String BASE_URL = "http://localhost:8080/";
    private static Retrofit retrofit;

    private RetrofitClient() {
    }

    static Interceptor interceptor = chain -> {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String emailToken = DataLocalManager.getInstance()
                .mySharedPreferences.getValue(DataLocalManager.KEY_EMAIL);
        String valueToken = DataLocalManager.getInstance()
                .mySharedPreferences.getValue(emailToken);
        builder.addHeader("token", "Bearer " + valueToken);
        return chain.proceed(builder.build());
    };

    static OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .addInterceptor(interceptor);

    // Phương thức synchronized để đảm bảo chỉ có một instance Retrofit duy nhất
    public static synchronized ApiService getApiService() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("dd-MM-yyyy")
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okBuilder.build())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
