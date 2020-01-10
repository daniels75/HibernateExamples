package org.daniels.examples.hibernate.repository;

import org.daniels.examples.hibernate.entities.Employee;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EmployeeCrudImpl implements EmployeeCrud {
	private EntityManager em;

	public EmployeeCrudImpl(EntityManager em){
		this.em = em;
	}

	public Employee saveEmployee(Employee employee) {
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
		return employee;
	}

	public Employee updateEmployee(Employee employee) {
		em.getTransaction().begin();
		em.merge(employee);
		em.getTransaction().commit();
		return employee;
	}

	public List<Employee> findAllEmployee() {
		Query query = em.createNamedQuery(Employee.FIND_ALL);
		return query.getResultList();
	}
}
