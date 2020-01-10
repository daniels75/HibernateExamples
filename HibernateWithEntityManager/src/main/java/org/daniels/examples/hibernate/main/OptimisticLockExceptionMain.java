package org.daniels.examples.hibernate.main;

import org.daniels.examples.hibernate.entities.Employee;
import org.daniels.examples.hibernate.repository.EmployeeCrud;
import org.daniels.examples.hibernate.repository.EmployeeCrudImpl;

import java.util.List;

public class OptimisticLockExceptionMain {


    public static void main(String args[]) {
        EmployeeCrud employeeCrud = new EmployeeCrudImpl();

        List<Employee> employees = employeeCrud.findAllEmployee();
        if (!employees.isEmpty()) {
            Employee employee = employees.get(0);
            employee.setFirstName("Roland4");
            employeeCrud.updateEmployee(employee);

            // Optimistic lock exception
            // here version is still not up-to-date
            // e.g. Roland3 update change version from 1->2
            // but here employee still contains "old" version 1
            // during an update javax.persistence.OptimisticLockException is throw
            employeeCrud.updateEmployee(employee);
        } else {
            Employee employee = new Employee();
            employee.setFirstName("Roland1");
            employeeCrud.saveEmployee(employee);
        }
    }

}
