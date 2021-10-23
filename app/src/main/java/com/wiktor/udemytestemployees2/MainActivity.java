package com.wiktor.udemytestemployees2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wiktor.udemytestemployees2.adapteers.EmployeeAdapter;
import com.wiktor.udemytestemployees2.api.ApiFactory;
import com.wiktor.udemytestemployees2.api.ApiService;
import com.wiktor.udemytestemployees2.pojo.Employee;
import com.wiktor.udemytestemployees2.pojo.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    List<Employee> employeeList;
    private RecyclerView recyclerViewEmployees;
    private EmployeeAdapter adapter;
    //http://gitlab.65apps.com/65gb/static/raw/master/testTask.json

    private Disposable disposable;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new EmployeeAdapter();
        adapter.setEmployees(new ArrayList<>());

/*        Employee employee1 = new Employee();
        employee1.setName("Виктор");
        employee1.setlName("Иванов");
        Employee employee2 = new Employee();
        employee2.setName("Сергей");
        employee2.setlName("Сидоров");
        employeeList.add(employee1);
        employeeList.add(employee2);
        adapter.setEmployees(employeeList);*/
        employeeList = new ArrayList<>();

        recyclerViewEmployees = findViewById(R.id.recyclerView_employees);
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployees.setAdapter(adapter);


        // получаем ApiFactory
        ApiFactory apiFactory = ApiFactory.getInstance();

        // получаем ApiService
        ApiService apiService = apiFactory.getApiService();

        //Получаем данные
        //Добавляем методы:
        // получаем данные
        // .subscribeOn() --- чтобы показать в каком протоке все делать. Все обращения к бд и запросы к сети делаются в Schedulers.io()
        // принимем данные
        // .observeOn(AndroidSchedulers.mainThread()) --- указать в каком потоке будем принимать данные. Принимать данные будем уже в глакном потоке
        // Что делать после получения данных
        // .subscribe(new Consumer, new Consumer )

        // Если пользователь выходит из приложения, я процесс еше работает в фоновом режиме, то происходит утечка памяти.
        // Для этого нужно привести к типу Disposable(перевод: выбрасываемый или одноразовый)( Disposable disposable = apiService.getEmployees()). После закрытия приложения у объекта Disposable вызвать метод  disposable.dispose();
                        /*if (disposable != null) {
                            disposable.dispose();
                        }*/
        // Если объектов Disposable много, то все они складываются в объект CompositeDisposable и закрываются все сразу.
        compositeDisposable = new CompositeDisposable();
        disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        adapter.setEmployees(employeeResponse.getEmployees());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "ошибка полученя данных " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }
}