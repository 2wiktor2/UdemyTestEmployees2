package com.wiktor.udemytestemployees2.api;

import com.wiktor.udemytestemployees2.pojo.EmployeeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// здесь указываются все запрося на сайт
public interface ApiService {

    // первое что возвращается в запросе - response. Поэтому возвращаемый тип будет  EmployeeResponse
    // название метода getEmployees() без параметров
    // Если указать таким образом ( EmployeeResponse getEmployees()), то мы не сможем слушать результаты запроса
    // нужно обернуть EmployeeResponse в объект Observable (io.reacrivex)

    //После аннотации @GET указать ендпроинт

    // Принцип работы:
    // retrofit построит запрос с базовым url и когда мы вызовем метод getEmployees() он добавит к запросу "testTask.json"
    // и вернет EmployeeResponse приведенный к типу Observable
    @GET("testTask.json")
    Observable<EmployeeResponse> getEmployees();
    // В ApiFactory нужно добавить метод который будет возвращать ApiService


    //Для передачи параметров в ручку
    // @GET("testTask.json")
    // Observable<EmployeeResponse> getEmployeesTest(@Query("api_key") int apiKey, @Query("leng") String lang);
    // потом передать параметры при вызове этого метода

    //Если в адрес нужно подставить значение
    @GET("testTask.json/{id}/video")
    Observable<EmployeeResponse> getEmployeesTest(@Path("id") int id, @Query("api_key") int apiKey, @Query("leng") String lang);
}
