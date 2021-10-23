package com.wiktor.udemytestemployees2.screens.employees;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wiktor.udemytestemployees2.R;
import com.wiktor.udemytestemployees2.adapteers.EmployeeAdapter;
import com.wiktor.udemytestemployees2.pojo.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity implements EmployeeListView {

    List<Employee> employeeList;
    private RecyclerView recyclerViewEmployees;
    private EmployeeAdapter adapter;
    //http://gitlab.65apps.com/65gb/static/raw/master/testTask.json

    //Ссылка на презентер
    private EmployeeListPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        presenter = new EmployeeListPresenter(this);


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


        presenter.loadData();
    }

/*    // Чтобы отобразить данные активность должна содержать метод showData(). Принимает лист сотрудников
    public void showData(List<Employee> employees) {
        adapter.setEmployees(employees);
    }

    // Показать тост при ошибке со связью
    public void showError(Throwable throwable) {
        Toast.makeText(EmployeeListActivity.this, "ошибка полученя данных " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }*/


    @Override
    public void showData(List<Employee> employees) {
        adapter.setEmployees(employees);
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(EmployeeListActivity.this, "ошибка полученя данных " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.disposeDisposable();
    }

}