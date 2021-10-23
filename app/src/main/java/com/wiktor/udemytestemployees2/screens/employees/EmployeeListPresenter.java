package com.wiktor.udemytestemployees2.screens.employees;

import com.wiktor.udemytestemployees2.api.ApiFactory;
import com.wiktor.udemytestemployees2.api.ApiService;
import com.wiktor.udemytestemployees2.pojo.EmployeeResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListPresenter {


    private CompositeDisposable compositeDisposable;
    private EmployeeListView view;

    public EmployeeListPresenter(EmployeeListView view) {
        this.view = view;
    }

    public void loadData() {

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
        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        //Устанавливать список сотрудников должна view в MVP
                        //adapter.setEmployees(employeeResponse.getEmployees());

                        view.showData(employeeResponse.getEmployees());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //Показывать тост должна view в MVP
                        // Toast.makeText(EmployeeListActivity.this, "ошибка полученя данных " + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                        view.showError(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
