package com.wiktor.udemytestemployees2.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static Retrofit retrofit;
    // http://gitlab.65apps.com/65gb/static/raw/master/testTask.json
    private static final String BASE_URL = "http://gitlab.65apps.com/65gb/static/raw/master/";

    //Класс должен сожержать синглтон
    private static ApiFactory apiFactory;

    public static ApiFactory getInstance() {
        if (apiFactory == null) {
            apiFactory = new ApiFactory();
        }
        return apiFactory;
    }

    // чтобы невозможно было создать объект класса ApiFactory, необходимо создать приватный конструктор без параметров
    private ApiFactory() {
        // в нем нужно создать объект Retrofit и указать ему
        // каким образом приобразовывать JSON в объект  .addConverterFactory(). В качестве параметра передать GsonConverterFactory.create()
        // добавить  .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) для того чтобы в процессе получения данных слушать события.
        // добавить базовый url который будет использоваться. Базовый url обязательно должен заканчиваться слэшом. Все остальное называется endPoint и указывается в другом месте.
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }


    // метод который  возвращает ApiService
    // Получать ApiService мы будем получать из ретрофита
    public ApiService getApiService() {

        return retrofit.create(ApiService.class);
    }
}
