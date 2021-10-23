package com.wiktor.udemytestemployees2.screens.employees;

import com.wiktor.udemytestemployees2.pojo.Employee;

import java.util.List;

public interface EmployeeListView {

    void showData(List<Employee> employees);

    void showError(Throwable throwable);
}
