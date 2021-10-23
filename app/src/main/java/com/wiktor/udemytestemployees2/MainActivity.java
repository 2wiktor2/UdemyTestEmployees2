package com.wiktor.udemytestemployees2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wiktor.udemytestemployees2.adapteers.EmployeeAdapter;
import com.wiktor.udemytestemployees2.pojo.Employee;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Employee> employeeList;
    private RecyclerView recyclerViewEmployees;
    private EmployeeAdapter adapter;
    //http://gitlab.65apps.com/65gb/static/raw/master/testTask.json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new EmployeeAdapter();

        employeeList = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setName("Виктор");
        employee1.setlName("Иванов");
        Employee employee2 = new Employee();
        employee2.setName("Сергей");
        employee2.setlName("Сидоров");
        employeeList.add(employee1);
        employeeList.add(employee2);
        adapter.setEmployees(employeeList);


        recyclerViewEmployees = findViewById(R.id.recyclerView_employees);
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployees.setAdapter(adapter);
    }
}