package org.daniels.examples.hibernate.repository;

import org.daniels.examples.hibernate.entities.Employee;
import org.daniels.examples.hibernate.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EmployeeCrudImpl implements EmployeeCrud {
	private EntityManager em;

	public EmployeeCrudImpl(EntityManager em){
		this.em = em;
	}

	public EmployeeCrudImpl(){
	}

	public Employee saveEmployee(Employee employee) {
		EntityManager em = HibernateUtil.createEntityManager();
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
		return employee;
	}

	public Employee updateEmployee(Employee employee) {
		EntityManager em = HibernateUtil.createEntityManager();
		em.getTransaction().begin();
		em.merge(employee);
		em.getTransaction().commit();
		return employee;
	}

	public List<Employee> findAllEmployee() {
		EntityManager em = HibernateUtil.createEntityManager();
		Query query = em.createNamedQuery(Employee.FIND_ALL);
		return query.getResultList();
	}
}
