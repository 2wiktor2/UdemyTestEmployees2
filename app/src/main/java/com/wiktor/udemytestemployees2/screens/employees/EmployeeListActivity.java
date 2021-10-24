package com.wiktor.udemytestemployees2.screens.employees;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wiktor.udemytestemployees2.R;
import com.wiktor.udemytestemployees2.adapteers.EmployeeAdapter;
import com.wiktor.udemytestemployees2.pojo.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {

    List<Employee> employeeList;
    private RecyclerView recyclerViewEmployees;
    private EmployeeAdapter adapter;
    //http://gitlab.65apps.com/65gb/static/raw/master/testTask.json

    private EmployeeViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

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

        viewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        //Подписаться на изменения в базе данных
        viewModel.getEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                adapter.setEmployees(employees);
            }
        });

        //подписаться на ошибки
        viewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable != null) {
                    Toast.makeText(EmployeeListActivity.this, "Ошибка полученя данных", Toast.LENGTH_SHORT).show();
                    //отчистка ошибки
                    viewModel.clearErrors();
                }
            }
        });
        viewModel.loadData();

    }

}